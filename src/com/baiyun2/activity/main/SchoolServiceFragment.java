package com.baiyun2.activity.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun2.activity.MyApplication;
import com.baiyun2.activity.R;
import com.baiyun2.activity.schoolservice.SLostFoundActivity;
import com.baiyun2.activity.schoolservice.SPhoneActivity;
import com.baiyun2.activity.webview.WebViewActiviry2;
import com.baiyun2.base.BaseAdAdapter;
import com.baiyun2.base.BaseFragment;
import com.baiyun2.http.HttpURL;
import com.baiyun2.httputils.SchoolLifeHttpUtils;
import com.baiyun2.httputils.SchoolServiceHttpUtils;
import com.baiyun2.httputils.VoHttpUtils;
import com.baiyun2.sharepreferences.UserInfoSP;
import com.baiyun2.vo.parcelable.Vo1Par;
import com.baiyun2.vo.parcelable.VoPicPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SchoolServiceFragment extends BaseFragment {
	
	/* 滚动图片  开始 */
	private ViewPager viewPager = null;
	private BaseAdAdapter pagerAdapter;
	private List<VoPicPar> voPicPars = new ArrayList<VoPicPar>();
	private ArrayList<View> views = new ArrayList<View>();
	private List<ImageView> imageViews = new ArrayList<ImageView>(4);//广告图片显示组件
	private TextView tvTitle;//广告标题
	private RadioButton rb_0, rb_1, rb_2, rb_3;

	private ScheduledExecutorService scheduledExecutorService;// 控制viewpager自动滑动
	private int currentItem = 0; // 当前图片的索引号
	/* 滚动图片  结束 */
	
	private Button btnSchedule;// 课表查询
	private Button btnScore;// 成绩查询
	private Button btnLostFound;// 失物招领
	private Button btnTeach;// 教学任务
	private Button btnExam;// 考试安排
	private Button btnUtilities;// 水电查询
	private Button btnRepairs;// 故障报修
	private Button btnPhone;// 办公电话
	private Button btnLibrary;// 图书馆

	private VoHttpUtils voHttpUtils;
	private String goRepairsUrl = null;// 故障报修的跳转链接
	
	private UserInfoSP userInfoSP;

	public static SchoolServiceFragment newInstance() {
		return new SchoolServiceFragment();
	}

	public SchoolServiceFragment() {

	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 启动循环线程
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		scheduledExecutorService.shutdown();// 停止循环线程
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		voHttpUtils = new VoHttpUtils(getActivity());
		userInfoSP = UserInfoSP.getSingleInstance(getActivity());
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_school_service;
	}

	@Override
	public void createMyView(View rootView) {
		initView(rootView);
		getNetData();
		
		initADs(rootView);
		getAd();
	}
	
	private void initView(View rootView){
		// 1.课表查询
		btnSchedule = (Button) rootView.findViewById(R.id.btn_schedule);
		btnSchedule.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((MyApplication)getActivity().getApplication()).isLogin()) {
					Intent intent = new Intent(getActivity(), WebViewActiviry2.class);
					intent.putExtra(WebViewActiviry2.KEY_URL_FULL, HttpURL.S_CLASS_INQUIRY+userInfoSP.getUserName());
					intent.putExtra(WebViewActiviry2.KEY_TITLE, "课表查询");
					startActivity(intent);
				}else {
					toastMsg("请先登录！");
					((MainActivity)getActivity()).setTopBarTitle("用户登录");
					((MainActivity)getActivity()).switchFragment(2);
				}
			}
		});
		// 2.成绩查询
		btnScore = (Button) rootView.findViewById(R.id.btn_score);
		btnScore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebViewActiviry2.class);
				intent.putExtra(WebViewActiviry2.KEY_URL_FULL, HttpURL.S_SCORE_INQUIRY);
				intent.putExtra(WebViewActiviry2.KEY_TITLE, "成绩查询");
				startActivity(intent);
			}
		});

		// 3.失物招领
		btnLostFound = (Button) rootView.findViewById(R.id.btn_lost_found);
		btnLostFound.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SLostFoundActivity.class);
				getActivity().startActivity(intent);
			}
		});

		// 4.教学任务
		btnTeach = (Button) rootView.findViewById(R.id.btn_teach);
		btnTeach.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebViewActiviry2.class);
				intent.putExtra(WebViewActiviry2.KEY_URL_FULL, HttpURL.S_TEACH_TASK+userInfoSP.getUserName());
				intent.putExtra(WebViewActiviry2.KEY_TITLE, "教学任务");
				startActivity(intent);
			}
		});

		// 5.考试安排
		btnExam = (Button) rootView.findViewById(R.id.btn_exam);
		btnExam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebViewActiviry2.class);
				intent.putExtra(WebViewActiviry2.KEY_URL_FULL, HttpURL.S_EXAM+userInfoSP.getUserName());
				intent.putExtra(WebViewActiviry2.KEY_TITLE, "考试安排");
				startActivity(intent);
			}
		});

		// 6.水电查询
		btnUtilities = (Button) rootView.findViewById(R.id.btn_utilities);
		btnUtilities.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebViewActiviry2.class);
				intent.putExtra(WebViewActiviry2.KEY_URL_FULL, HttpURL.S_HYDROP_POWER+userInfoSP.getUserName());
				intent.putExtra(WebViewActiviry2.KEY_TITLE, "水电查询");
				startActivity(intent);
			}
		});

		// 7.故障报修
		btnRepairs = (Button) rootView.findViewById(R.id.btn_repairs);
		btnRepairs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (goRepairsUrl != null) {
					if (!(goRepairsUrl.contains("http://"))) {
						goRepairsUrl = "http://" + goRepairsUrl;
					}
					new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("跳转到：" + goRepairsUrl)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if (URLUtil.isNetworkUrl(goRepairsUrl)) {
										Uri uri = Uri.parse(goRepairsUrl);
										Intent intent = new Intent(Intent.ACTION_VIEW, uri);
										getActivity().startActivity(intent);
									} else {
										Toast.makeText(getActivity(), "网站链接不正确\n" + goRepairsUrl, Toast.LENGTH_SHORT).show();
									}
								}
							}).setNegativeButton("取消", null).create().show();
				}
			}
		});

		// 8.办公电话
		btnPhone = (Button) rootView.findViewById(R.id.btn_phone);
		btnPhone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SPhoneActivity.class);
				getActivity().startActivity(intent);
			}
		});

		// 9.图书馆
		btnLibrary = (Button) rootView.findViewById(R.id.btn_library);
		btnLibrary.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WebViewActiviry2.class);
				intent.putExtra(WebViewActiviry2.KEY_URL_FULL, HttpURL.S_LIBRARY+userInfoSP.getUserName());
				intent.putExtra(WebViewActiviry2.KEY_TITLE, "图书馆");
				startActivity(intent);
			}
		});
	}
	
	private void initADs(View rootView) {
		viewPager = (ViewPager) rootView.findViewById(R.id.vp_ad);
		tvTitle = (TextView)rootView.findViewById(R.id.tv_title);

		for (int i = 0; i < 4; i++) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.imageview_ad, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_ad);
			imageViews.add(imageView);
			views.add(view);
		}

		pagerAdapter = new BaseAdAdapter(views);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		rb_0 = (RadioButton) rootView.findViewById(R.id.rb_ad_0);
		rb_1 = (RadioButton) rootView.findViewById(R.id.rb_ad_1);
		rb_2 = (RadioButton) rootView.findViewById(R.id.rb_ad_2);
		rb_3 = (RadioButton) rootView.findViewById(R.id.rb_ad_3);
	}

	private void getNetData() {
		voHttpUtils.getVo1(HttpURL.S_REPAIRS, new VoHttpUtils.OnGetVo1Listener() {

			@Override
			public void onGetVo1(Vo1Par vo1Par) {
				// TODO Auto-generated method stub
				if (vo1Par != null) {
					goRepairsUrl = vo1Par.getUrl();
				}
			}
		});
	}
	
	//顶部图片
	private void getAd(){
		(new SchoolServiceHttpUtils(getActivity())).getAd(new SchoolServiceHttpUtils.onGetAdListener() {
			
			@Override
			public void onGetAd(List<VoPicPar> picPars) {
				if (picPars != null) {//广告图片
					voPicPars = picPars;
					
					//设置广告标题
					if (currentItem < voPicPars.size()) {
						String title = voPicPars.get(currentItem).getName().toString();
						if (!TextUtils.isEmpty(title)) {
							tvTitle.setText(title);
						}
					}else {
						tvTitle.setText("");
					}
					
					//设置显示广告图片
					int size = picPars.size();
					int forCount = 0;
					if (size > 4) {
						forCount = 4;
					} else {
						forCount = size;
					}
					for (int i = 0; i < forCount; i++) {
						VoPicPar picPar = picPars.get(i);
						String urlLast = picPar.getUrl();
						if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
							String url = HttpURL.HOST + urlLast.substring(1);
							ImageLoader.getInstance().displayImage(url, imageViews.get(i));
						}
					}
				}
			}
		});
	}
	
	/**
	 * 换行切换任务
	 */
	private class ScrollTask implements Runnable {
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % views.size();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
						
						if (currentItem < voPicPars.size()) {
							String title = voPicPars.get(currentItem).getName().toString();
							if (!TextUtils.isEmpty(title)) {
								tvTitle.setText(title);
							}
						}else {
							tvTitle.setText("");
						}
					}
				});
			}
		}
	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			currentItem = arg0;
			switch (arg0) {
			case 0:
				rb_0.setChecked(true);
				rb_1.setChecked(false);
				rb_2.setChecked(false);
				rb_3.setChecked(false);
				break;
			case 1:
				rb_0.setChecked(false);
				rb_1.setChecked(true);
				rb_2.setChecked(false);
				rb_3.setChecked(false);
				break;
			case 2:
				rb_0.setChecked(false);
				rb_1.setChecked(false);
				rb_2.setChecked(true);
				rb_3.setChecked(false);
				break;
			case 3:
				rb_0.setChecked(false);
				rb_1.setChecked(false);
				rb_2.setChecked(false);
				rb_3.setChecked(true);
				break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}

}
