package com.baiyun2.activity.home;

import android.support.v4.app.FragmentManager;

import com.baiyun2.activity.R;
import com.baiyun2.base.BaseFragmentActivity;
import com.baiyun2.util.ui.FragmentUtil;

public class NewsActivity extends BaseFragmentActivity {
	private FragmentManager fragmentManager;

	private NewsFragment newsFragment = null;

	@Override
	public void init() {
		setTopBarTitle("白云微报");
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager();
		
		showNewsFragment();
	}

	private void showNewsFragment() {
		if (newsFragment == null) {
			newsFragment = NewsFragment.newInstance();
		}
		FragmentUtil.replaceNormal(newsFragment, fragmentManager, R.id.fl_container_common);
	}

}
