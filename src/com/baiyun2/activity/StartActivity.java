package com.baiyun2.activity;

import com.baiyun2.activity.R;
import com.baiyun2.activity.main.MainActivity;
import com.baiyun2.base.BaseFragmentActivity;
import com.baiyun2.sharepreferences.AppSettingSP;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

/**
 * @author Holyn
 * @since 2015-1-18
 *
 */
public class StartActivity extends FragmentActivity {
	private AppSettingSP appSettingSP;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_start);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

		appSettingSP = AppSettingSP.getSingleInstance(this);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if (appSettingSP.getIsFirst()) {
					showGuideFragment();
					appSettingSP.setIsFirst(false);
				} else {
					Intent intent = new Intent(StartActivity.this, MainActivity.class);
					startActivity(intent);
					StartActivity.this.finish();
				}

			}
		}, 2000);
	}

	private void showGuideFragment() {
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_container_common, GuideFragment.newInstance()).commit();
	}

}
