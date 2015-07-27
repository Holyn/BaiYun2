package com.baiyun2.activity.webview;

import com.baiyun2.activity.R;
import com.baiyun2.http.HttpURL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * itent到此WebViewActiviry，
 * 需要putExtra两个参数：KEY_WEB_VIEW_TYPE与KEY_CONTENT_URL
 *
 */
public class WebViewActiviry2 extends FragmentActivity{
	public static final String KEY_URL_LAST = "key_urlLast";//url的后面部分
	public static final String KEY_URL_FULL = "key_url";//完整的url
	public static final String KEY_TITLE = "key_title";
	
	private FrameLayout flTopBar = null;
	private TextView tvTitle = null;
	private ImageButton ibBack = null, ibTopRight = null;
	
	private ProgressWebView webView = null;
	
	private String urlLast;
	private String urlFull;
	private String title;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_webview);
		webView = (ProgressWebView)findViewById(R.id.webview);
		
		setBackPressEnabled(true);
		
		Intent intent = getIntent();
		urlLast = intent.getStringExtra(KEY_URL_LAST);
		urlFull = intent.getStringExtra(KEY_URL_FULL);
		title = intent.getStringExtra(KEY_TITLE);
		setTopBarTitle(title);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
		webView.getSettings().setSupportZoom(true); // 可以缩放
		webView.getSettings().setDefaultZoom(ZoomDensity.CLOSE);// 默认缩放模式
		webView.getSettings().setUseWideViewPort(true); // 为图片添加放大缩小功能

		webView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				if (url != null && url.startsWith("http://"))
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});
		
		if (!TextUtils.isEmpty(urlFull)) {
			webView.loadUrl(urlFull);
		}else if (!TextUtils.isEmpty(urlLast)) {
			String url = HttpURL.HOST + urlLast;
			webView.loadUrl(url);
		}else {
			Toast.makeText(this, "图文链接为空", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 默认TopBar是显示的
	 */
	public void setTopBarEnable(boolean isEnable) {
		if (flTopBar == null) {
			flTopBar = (FrameLayout)findViewById(R.id.fl_actionbar);
		}
		if (isEnable) {
			flTopBar.setVisibility(View.VISIBLE);
		}else {
			flTopBar.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置TopBarTitle
	 */
	public void setTopBarTitle(String title){
		if (tvTitle == null) {
			tvTitle = (TextView)findViewById(R.id.tv_actionbar_title);
		}
		tvTitle.setText(title);
	}
	
	/**
	 * 默认时回退按钮是不可见的
	 */
	public void setBackPressEnabled(boolean isEnable) {
		if (ibBack == null) {
			ibBack = (ImageButton)findViewById(R.id.ib_actionbar_back);
		}
		if (isEnable) {
			ibBack.setVisibility(View.VISIBLE);
			ibBack.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}else {
			ibBack.setVisibility(View.GONE);
		}
	}
	/**
	 * 默认顶部导航栏右边的菜单是不可见的
	 */
	public void setTopBarRightBtnEnable(boolean isEnable) {
		if (ibTopRight == null) {
			ibTopRight = (ImageButton)findViewById(R.id.ib_actionbar_right);
		}
		if (isEnable) {
			ibTopRight.setVisibility(View.VISIBLE);
		}else {
			ibTopRight.setVisibility(View.GONE);
		}
	}
	
	public ImageButton getTopBarRightImageButton() {
		if (ibTopRight == null) {
			setTopBarRightBtnEnable(true);
		}
		return ibTopRight;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		webView.reload();
		super.onPause();
	}
}
