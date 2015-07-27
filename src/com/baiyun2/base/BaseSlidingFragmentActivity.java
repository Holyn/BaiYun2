package com.baiyun2.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyun2.activity.R;
import com.baiyun2.activity.main.SlideMenuFragment;
import com.baiyun2.custom.ButteryProgressBar;
import com.baiyun2.util.ScreenUtil;
import com.holyn.slidingmenu.lib.SlidingMenu;
import com.holyn.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class BaseSlidingFragmentActivity extends SlidingFragmentActivity{
	
	protected SlidingMenu slidingMenu;
	private SlideMenuFragment slideMenuFragment;
	
	private FrameLayout flTopBar = null;
	private TextView tvTitle = null,tvTitlePinyin = null;
	private ImageButton ibBack = null, ibDrawer = null;
	private LinearLayout llConsult = null;
	
	public FrameLayout loadingBar;
	
	public ImageView ivActionBarLine = null;
	
	private boolean isLLConsultEnable = false;
	private boolean isTopBarTitlePinyinEnable = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initRightSlidingMenu();
		
		//设置条形progressbar
		final ButteryProgressBar progressBar = new ButteryProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				10));
		loadingBar = (FrameLayout)findViewById(R.id.fl_progressbar);
		loadingBar.addView(progressBar);
		
		init();
	}
	
	/**
	 * 此方法在onCreate里面执行，唔需重写onCreate方法
	 * 已经setContentView(R.layout.activity_common);
	 */
	public abstract void init();
	
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
	 * 读取Title
	 */
	public String getTopBarTitle(){
		if (tvTitle == null) {
			tvTitle = (TextView)findViewById(R.id.tv_actionbar_title);
		}
		String title = tvTitle.getText().toString();
		return title;
	}
	
	/**
	 * 设置标题的拼音是否可见,默认不可见
	 */
	public void setTopBarTitlePinyinEnable(boolean isEnable) {
		isTopBarTitlePinyinEnable = isEnable;
		if (tvTitlePinyin == null) {
			tvTitlePinyin = (TextView)findViewById(R.id.tv_actionbar_title_pinyin);
		}
		if (isEnable) {
			tvTitlePinyin.setVisibility(View.VISIBLE);
		}else {
			tvTitlePinyin.setVisibility(View.GONE);
		}
	}
	
	public boolean isTopBarTitlePinyinEnable() {
		return isTopBarTitlePinyinEnable;
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
	 * 控制抽屉开闭的按钮
	 */
	public void setDrawerBtnEnable(boolean isEnable) {
		if (ibDrawer == null) {
			ibDrawer = (ImageButton)findViewById(R.id.ib_actionbar_drawer);
		}
		if (isEnable) {
			ibDrawer.setVisibility(View.VISIBLE);
			ibDrawer.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showSlideMenuFragment();
				}
			});
		}else {
			ibDrawer.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置查询按钮是否可见，默认不可见
	 */
	public void setLLConsultEnable(boolean isEnable) {
		isLLConsultEnable = isEnable;
		if (llConsult == null) {
			llConsult = (LinearLayout)findViewById(R.id.ll_consult);
		}
		if (isEnable) {
			llConsult.setVisibility(View.VISIBLE);
		}else {
			llConsult.setVisibility(View.GONE);
		}
	}
	
	public LinearLayout getLLConsult() {
		setLLConsultEnable(true);
		return llConsult;
	}
	
	public boolean isLLConsultEnable() {
		return isLLConsultEnable;
	}
	
	public void setLoadingBarVisible() {
		loadingBar.setVisibility(View.VISIBLE);
	}
	
	public void setLoadingBarGone() {
		loadingBar.setVisibility(View.GONE);
	}
	
	/**
	 * 设置标题栏底部的分割线为黑色，默认是黑色的
	 * 
	 */
	public void setActionBarLineBlack() {
		if (ivActionBarLine == null) {
			ivActionBarLine = (ImageView)findViewById(R.id.iv_action_bar_line);
		}
		ivActionBarLine.setImageResource(R.drawable.ic_line_black);
	}
	
	/**
	 * 设置标题栏底部的分割线为彩色，默认是黑色的
	 * 
	 */
	public void setActionBarLineColor() {
		if (ivActionBarLine == null) {
			ivActionBarLine = (ImageView)findViewById(R.id.iv_action_bar_line);
		}
		ivActionBarLine.setImageResource(R.drawable.ic_line_color);
	}
	
    private void initRightSlidingMenu() {
		setBehindContentView(R.layout.sliding_menu_layout);
		FragmentTransaction leftFragementTransaction = getSupportFragmentManager().beginTransaction();
		slideMenuFragment = SlideMenuFragment.newInstance();
		leftFragementTransaction.replace(R.id.fl_sliding_menu, slideMenuFragment);
		leftFragementTransaction.commit();
		// customize the SlidingMenu
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT);// 设置是右滑
		slidingMenu.setBehindOffset(ScreenUtil.getScreenWidth(this)*1/4);// 设置菜单宽度
		slidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//设置手势模式
		slidingMenu.setShadowDrawable(R.drawable.yejk_shadow);// 设置左菜单阴影图片
		slidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		slidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
    }
    
    public void showSlideMenuFragment() {
		slidingMenu.showMenu();
	}
    
    public void closeSlideMenuFragmetAndShowContent() {
		slidingMenu.showContent();
	}
    
	public SlidingMenu getSlidingMenu() {
		return super.getSlidingMenu();
	}

	public SlideMenuFragment getSlideMenuFragment() {
		return slideMenuFragment;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	
}
