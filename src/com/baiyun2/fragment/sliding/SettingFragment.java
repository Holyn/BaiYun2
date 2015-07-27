package com.baiyun2.fragment.sliding;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun2.activity.MyApplication;
import com.baiyun2.activity.R;
import com.baiyun2.activity.setting.SettingItemActivity;
import com.baiyun2.baidu_push.BaiduPushManager;
import com.baiyun2.base.BaseFragment;
import com.baiyun2.custom.DialogFactory;
import com.baiyun2.custom.SlipButton;
import com.baiyun2.httputils.SlideMenuHttpUtils;
import com.baiyun2.sharepreferences.AppSettingSP;
import com.baiyun2.vo.parcelable.VersionPar;

public class SettingFragment extends BaseFragment{
	private SlipButton sbChangePush;
	private TextView tvChangePwd;
	private TextView tvVersionUpdate;
	private TextView tvHelp;
	private TextView tvFeedback;
	private TextView tvAbout;
	
	public static SettingFragment newInstance() {
		return new SettingFragment();
	}
	
	public SettingFragment() {
		
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_setting;
	}

	@Override
	public void createMyView(View rootView) {
		initView(rootView);
		initListener();
	}
	
	private void initView(View rootView){
		sbChangePush = (SlipButton)rootView.findViewById(R.id.slip_utton);
		if (AppSettingSP.getSingleInstance(getActivity()).isBaiduPushEnable()) {
			sbChangePush.setCheck(true);
		}else {
			sbChangePush.setCheck(false);
		}
		
		tvChangePwd = (TextView)rootView.findViewById(R.id.tv_change_pwd);
		tvVersionUpdate = (TextView)rootView.findViewById(R.id.tv_version_update);
		tvHelp = (TextView)rootView.findViewById(R.id.tv_help);
		tvFeedback = (TextView)rootView.findViewById(R.id.tv_feedback);
		tvAbout = (TextView)rootView.findViewById(R.id.tv_about);
	}
	
	private void initListener(){
		sbChangePush.SetOnChangedListener(new SlipButton.onChangeListener() {
			
			@Override
			public void OnChanged(boolean CheckState) {
				AppSettingSP.getSingleInstance(getActivity()).setIsBaiduPushEnable(CheckState);
				if (!CheckState) {
					BaiduPushManager.stopWork(getActivity());
				}
			}
		});
		
		tvChangePwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (((MyApplication)getActivity().getApplication()).isLogin()) {
					Intent intent = new Intent(getActivity(), SettingItemActivity.class);
					intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.CHANGE_PWD);
					startActivity(intent);
				}else {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		tvVersionUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkVersion();
			}
		});
		
		tvHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.HELP);
				startActivity(intent);
			}
		});
		
		tvFeedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.FEEDBACK);
				startActivity(intent);
			}
		});
		
		tvAbout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.ABOUT);
				startActivity(intent);
			}
		});
	}
	
	private void checkVersion(){
		showLoadingDialog();
		try {
			String curVersionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
			final Float curVersionNameFloat = Float.parseFloat(curVersionName);
			
			new SlideMenuHttpUtils(getActivity()).getVersion(curVersionName, new SlideMenuHttpUtils.OnGetVersionListener() {
				
				@Override
				public void onGetVersion(VersionPar versionPar) {
					// TODO Auto-generated method stub
					if (versionPar != null) {
						String serVersionName = versionPar.getLatestVersion();
						Float serVersionNameFloat = Float.parseFloat(serVersionName);
						if (curVersionNameFloat < serVersionNameFloat) {//有新的版本
							DialogFactory.showVersionNotice(getActivity(), versionPar);
						}else {
							Toast.makeText(getActivity(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
						}
					}
					
					closeLoadingDialog();
				}
			});
			
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
