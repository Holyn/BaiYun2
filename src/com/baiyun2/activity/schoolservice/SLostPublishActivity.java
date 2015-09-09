package com.baiyun2.activity.schoolservice;

import com.baiyun2.activity.R;
import com.baiyun2.base.BaseFragmentActivity;
import com.baiyun2.util.ui.FragmentUtil;

import android.support.v4.app.FragmentManager;

public class SLostPublishActivity extends BaseFragmentActivity{
	private FragmentManager fragmentManager;
	
	private SLostPublishFragment publishFragment;
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setBackPressEnabled(true);
		setTopBarTitle("发布失物");
		fragmentManager = getSupportFragmentManager();
		
		showSLostPublishFragment();
	}

	private void showSLostPublishFragment(){
		if (publishFragment == null) {
			publishFragment = SLostPublishFragment.newInstance();
		}
		FragmentUtil.replaceNormal(publishFragment, fragmentManager, R.id.fl_container_common);
	}
}
