package com.baiyun2.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baiyun2.activity.R;
import com.baiyun2.activity.MyApplication;
import com.baiyun2.activity.recruit.ApplyFragment;
import com.baiyun2.activity.recruit.EnterFragment;
import com.baiyun2.activity.recruit.RSearchActivity;
import com.baiyun2.activity.recruit.RecruitTypeFragment;
import com.baiyun2.activity.recruit.RecruitTypeFragment2;
import com.baiyun2.activity.recruit.TuitionFragment;
import com.baiyun2.activity.recruit.TuitionFragment2;
import com.baiyun2.activity.webview.WebViewActiviry;
import com.baiyun2.base.BaseFragment;
import com.baiyun2.httputils.RecruitHttpUtils;

public class RecruitFragment extends BaseFragment{
	private MyApplication myApplication = null;
	
	private RadioButton rb_1,rb_2,rb_3,rb_4;
	private View v_line_1,v_line_2,v_line_3,v_line_4;
	
	private LinearLayout llConsult;//右上角的查询按钮布局
	
	private FragmentManager fragmentManager;
	private Fragment curFragment;
	
	private int curPosition = -1;
//	private TuitionFragment tuitionFragment = null;
	private TuitionFragment2 tuitionFragment2 = null;
	private EnterFragment enterFragment = null;
//	private RecruitTypeFragment consultFragment = null;
	private RecruitTypeFragment2 consultFragment2 = null;
	private ApplyFragment applyFragment = null;
	
	public static RecruitFragment newInstance() {
		return new RecruitFragment();
	}

	public RecruitFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication = (MyApplication)getActivity().getApplicationContext();
		curPosition = myApplication.getCurRecruitFragmentPosition();
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_recruit;
	}

	@Override
	public void createMyView(View rootView) {
		fragmentManager = getChildFragmentManager();
		initView(rootView);
		
		applyFragment = ApplyFragment.newInstance();
		curFragment = applyFragment;
		fragmentManager.beginTransaction().add(R.id.fl_container, curFragment).commit();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((MainActivity)getActivity()).setLLConsultEnable(true);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((MainActivity)getActivity()).setLLConsultEnable(false);
	}

	private void initView(View rootView){
		rb_1 = (RadioButton)rootView.findViewById(R.id.rb_1);
		rb_2 = (RadioButton)rootView.findViewById(R.id.rb_2);
		rb_3 = (RadioButton)rootView.findViewById(R.id.rb_3);
		rb_4 = (RadioButton)rootView.findViewById(R.id.rb_4);
		
		v_line_1 = (View)rootView.findViewById(R.id.v_line_1);
		v_line_2 = (View)rootView.findViewById(R.id.v_line_2);
		v_line_3 = (View)rootView.findViewById(R.id.v_line_3);
		v_line_4 = (View)rootView.findViewById(R.id.v_line_4);
		
		llConsult = ((MainActivity)getActivity()).getLLConsult();
		llConsult.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				new KeFuManager(getActivity()).startChat();
				showLoadingDialog();
				(new RecruitHttpUtils(getActivity())).getRCUrl("3", new RecruitHttpUtils.OnGetRCUrlListener() {
					
					@Override
					public void onGetRCUrl(String url) {
						// TODO Auto-generated method stub
						closeLoadingDialog();
						if (url != null) {
							Intent intent = new Intent(getActivity(), WebViewActiviry.class);
							intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.H_Consult);
							intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, url);
							getActivity().startActivity(intent);
						}
					}
				});
			}
		});
		
		initRadioButtonListener();
	}
	
	private void initRadioButtonListener(){
		rb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(true);
					rb_2.setChecked(false);
					rb_3.setChecked(false);
					rb_4.setChecked(false);
					
					v_line_1.setVisibility(View.VISIBLE);
//					switchFragment(0);
				}else {
					v_line_1.setVisibility(View.INVISIBLE);
				}
				
			}
			
		});
		rb_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(false);
					rb_2.setChecked(true);
					rb_3.setChecked(false);
					rb_4.setChecked(false);
					
					v_line_2.setVisibility(View.VISIBLE);
//					switchFragment(1);
				}else {
					v_line_2.setVisibility(View.INVISIBLE);
				}
			}
			
		});
		rb_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(false);
					rb_2.setChecked(false);
					rb_3.setChecked(true);
					rb_4.setChecked(false);
					
					v_line_3.setVisibility(View.VISIBLE);
//					switchFragment(2);
				}else {
					v_line_3.setVisibility(View.INVISIBLE);
				}
			}
			
		});
		rb_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(false);
					rb_2.setChecked(false);
					rb_3.setChecked(false);
					rb_4.setChecked(true);
					
					v_line_4.setVisibility(View.VISIBLE);
//					switchFragment(3);
				}else {
					v_line_4.setVisibility(View.INVISIBLE);
				}
			}
			
		});
	}
	
	public void switchFragment(int position) {
		Fragment nextFragment = null;
		if (position == 0) {
			if (tuitionFragment2 == null) {
				tuitionFragment2 = TuitionFragment2.newInstance();
			}
			nextFragment = tuitionFragment2;
		}else if (position == 1) {
			if (enterFragment == null) {
				enterFragment = EnterFragment.newInstance();
			}
			nextFragment = enterFragment;
		}else if (position == 2) {
			if (consultFragment2 == null) {
				consultFragment2 = RecruitTypeFragment2.newInstance();
			}
			nextFragment = consultFragment2;
		}else if (position == 3) {
			if (applyFragment == null) {
				applyFragment = ApplyFragment.newInstance();
			}
			nextFragment = applyFragment;
		}
		
		if (curPosition != position) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			if (!nextFragment.isAdded()) {    // 先判断是否被add过  
                transaction.hide(curFragment).add(R.id.fl_container, nextFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中  
            } else {  
                transaction.hide(curFragment).show(nextFragment).commit(); // 隐藏当前的fragment，显示下一个  
            } 
			
			/**
			 * remove了对应的Fragment之后，那么下次切换到这个Fragment就会重新OnResum，不可见是OnPause
			 */
			if (curFragment == applyFragment) {
				fragmentManager.beginTransaction().remove(curFragment).commit();
				applyFragment = null;
			}
			
			curPosition = position;
			curFragment = nextFragment;
		}
		myApplication.setCurRecruitFragmentPosition(position);
	}
}
