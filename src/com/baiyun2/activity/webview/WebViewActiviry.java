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
import android.widget.Button;
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
public class WebViewActiviry extends FragmentActivity{
	public static final String KEY_WEB_VIEW_TYPE = "key_web_view_type";
	public static final String KEY_CONTENT_URL = "key_content_url";
	
	public static final int NEWS_DETAIL = 1;//标题为白云微报
	public static final int VIDEO = 2;//标题为：视频白云
	public static final int JOB_RECRUIT = 3;//标题为:招聘信息
	public static final int COOPERATION = 4;//标题为:校企合作
	public static final int RECRUIT_PLAN = 5;//标题为:招生计划
	public static final int RECRUIT_INTRO = 6;//标题为:专业介绍
	
	public static final int BAIDU_PUSH = 7;//标题为：信息详情
	
	public static final int R_Consult = 8;//标题为：报名查询
	public static final int R_Register = 9;//标题为：网上报名
	
	public static final int H_Consult = 10;//标题为：招生咨询
	
	private FrameLayout flTopBar = null;
	private TextView tvTitle = null;
	private Button btnMenu2 = null;//定制多一个菜单
	private ImageButton ibBack = null, ibTopRight = null;
	
	private ProgressWebView webView = null;
	
	private String contentUrl;
	
	private boolean isShowUrl2 = false;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_webview);
		webView = (ProgressWebView)findViewById(R.id.webview);
		
		setBackPressEnabled(true);
		
		Intent intent = getIntent();
		int webViewType = intent.getIntExtra(KEY_WEB_VIEW_TYPE, 0);
		contentUrl = intent.getStringExtra(KEY_CONTENT_URL);
		
		if (webViewType == NEWS_DETAIL) {
			setTopBarTitle("白云微报");
		}else if (webViewType == VIDEO) {
			setTopBarTitle("视频白云");
			webView.getSettings().setPluginState(PluginState.ON);
			
//			webView.getSettings().setPluginsEnabled(true);
			webView.getSettings().setAllowFileAccess(true);
			webView.getSettings().setDefaultTextEncodingName("GBK");
		}else if (webViewType == JOB_RECRUIT) {
			setTopBarTitle("招聘信息");
		}else if (webViewType == COOPERATION) {
			setTopBarTitle("校企合作");
		}else if (webViewType == RECRUIT_PLAN) {
			setTopBarTitle("招生计划");
		}else if (webViewType == RECRUIT_INTRO) {
			setTopBarTitle("专业介绍");
		}else if (webViewType == BAIDU_PUSH) {
			setTopBarTitle("信息详情");
		}else if (webViewType == R_Consult) {
			setTopBarTitle("报名查询");
		}else if (webViewType == R_Register) {
			setTopBarTitle("网上报名");
		}else if (webViewType == H_Consult) {
			setTopBarTitle("招生咨询");
			setBtnMenu2Name("我要提问");
			btnMenu2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					webView.loadUrl("http://zsbm.bvtczsb.com/Common/GuestWrite.aspx?id=585&modelId=21&nodeId=585");
					isShowUrl2 = true;
				}
			});
		}
		
		if (contentUrl == null) {
			Toast.makeText(this, "图文链接为空", Toast.LENGTH_SHORT).show();
		}else {
			if (contentUrl.equalsIgnoreCase("")) {
			}else {
				if (webViewType == BAIDU_PUSH) {
					contentUrl = HttpURL.HOST+"/"+contentUrl;//构造完整Html5路径
				}else if (webViewType == R_Consult || webViewType == R_Register || webViewType == H_Consult) {
					
				}else {
					contentUrl = HttpURL.HOST+contentUrl;//构造完整Html5路径
				}
//				System.out.println("====> WebView-URL: "+contentUrl);
				
				
				webView.getSettings().setJavaScriptEnabled(true);
				webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
				webView.getSettings().setSupportZoom(true); // 可以缩放
				webView.getSettings().setDefaultZoom(ZoomDensity.CLOSE);// 默认缩放模式
				webView.getSettings().setUseWideViewPort(true);  //为图片添加放大缩小功能
				
				webView.setDownloadListener(new DownloadListener() {
		            @Override
		            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
		                if (url != null && url.startsWith("http://"))
		                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		            }
		        });
				webView.loadUrl(contentUrl);
			}
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
	 * 默认顶部导航栏右边的第二个菜单是不可见的
	 */
	public void setBtnMenu2Enable(boolean isEnable){
		if (btnMenu2 == null) {
			btnMenu2 = (Button)findViewById(R.id.btn_menu_2);
		}
		if (isEnable) {
			btnMenu2.setVisibility(View.VISIBLE);
		}else {
			btnMenu2.setVisibility(View.GONE);
		}
	}
	
	public void setBtnMenu2Name(String menuName) {
		setBtnMenu2Enable(true);
		if (btnMenu2 == null) {
			btnMenu2 = (Button)findViewById(R.id.btn_menu_2);
		}
		if (!TextUtils.isEmpty(menuName)) {
			btnMenu2.setText(menuName);
		}
	}
	
	public Button getBtnMenu2() {
		if (btnMenu2 == null) {
			btnMenu2 = (Button)findViewById(R.id.btn_menu_2);
		}
		return btnMenu2;
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

	public void onBackPressed() {
		if (isShowUrl2) {
			isShowUrl2 = false;
			webView.loadUrl(contentUrl);
		}else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onPause() {
		webView.reload();
		super.onPause();
	}
}
