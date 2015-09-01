package com.baiyun2.activity.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baiyun2.activity.R;
import com.baiyun2.activity.life.LAssociationActivity;
import com.baiyun2.activity.life.LGuideActivity;
import com.baiyun2.activity.life.LModelActivity;
import com.baiyun2.activity.life.LNewsActivity;
import com.baiyun2.base.BaseAdAdapter;
import com.baiyun2.base.BaseFragment;
import com.baiyun2.http.HttpURL;
import com.baiyun2.httputils.SchoolLifeHttpUtils;
import com.baiyun2.vo.parcelable.VoPicPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SchoolLifeFragment extends BaseFragment{
	
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
	
	private Button btnNews;//学工动态
	private Button btnAssociation;//学生社团
	private Button btnArt;//文化艺术
	private Button btnScience;//科技创新
	private Button btnModel;//榜样风云
	private Button btnGuide;//服务指南
	
	
	public static SchoolLifeFragment newInstance() {
		return new SchoolLifeFragment();
	}

	public SchoolLifeFragment() {
		
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_school_life;
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
	public void createMyView(View rootView) {
		
		initADs(rootView);
		getAd();
		
		//党建合作（党团活动）
		btnNews = (Button)rootView.findViewById(R.id.btn_news);
		btnNews.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LNewsActivity.class);
				intent.putExtra(LNewsActivity.NEWS_ID_VALUE, LNewsActivity.NEWS_ID_24);
				getActivity().startActivity(intent);
			}
		});
		
		btnAssociation = (Button)rootView.findViewById(R.id.btn_association);
		btnAssociation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LAssociationActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		//文化艺术
		btnArt = (Button)rootView.findViewById(R.id.btn_art);
		btnArt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LNewsActivity.class);
				intent.putExtra(LNewsActivity.NEWS_ID_VALUE, LNewsActivity.NEWS_ID_26);
				getActivity().startActivity(intent);
			}
		});
		
		btnScience = (Button)rootView.findViewById(R.id.btn_science);
		btnScience.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LNewsActivity.class);
				intent.putExtra(LNewsActivity.NEWS_ID_VALUE, LNewsActivity.NEWS_ID_27);
				getActivity().startActivity(intent);
			}
		});
		
		btnModel = (Button)rootView.findViewById(R.id.btn_model);
		btnModel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LModelActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		btnGuide = (Button)rootView.findViewById(R.id.btn_guide);
		btnGuide.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LGuideActivity.class);
				getActivity().startActivity(intent);
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
	
	//顶部图片
	private void getAd(){
		(new SchoolLifeHttpUtils(getActivity())).getAd(new SchoolLifeHttpUtils.onGetAdListener() {
			
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
