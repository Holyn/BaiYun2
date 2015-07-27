package com.baiyun2.activity.life;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baiyun2.activity.R;
import com.baiyun2.activity.webview.WebViewFragment2;
import com.baiyun2.base.BaseFragmentActivity;
import com.baiyun2.util.ui.FragmentUtil;

public class LModelActivity extends BaseFragmentActivity{
	private FragmentManager fragmentManager;
	
	private LModelFragment modelFragment = null;
	private WebViewFragment2 webViewFragment2 = null;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager();
		
		showLModelFragment();
	}
	
	private void showLModelFragment(){
		if (modelFragment == null) {
			modelFragment = LModelFragment.newInstance();
		}
		FragmentUtil.replaceNormal(modelFragment, fragmentManager, R.id.fl_container_common);
	}
	
	public void showWebViewFragment2(String urlLast, String title){
		Bundle args = new Bundle();
		args.putString(WebViewFragment2.KEY_URL_LAST, urlLast);
		args.putString(WebViewFragment2.KEY_TITLE, title);
		if (webViewFragment2 == null) {
			webViewFragment2 = WebViewFragment2.newInstance();
		}
		FragmentUtil.replaceAddToBack(webViewFragment2, fragmentManager, R.id.fl_container_common, args);
	}

}
