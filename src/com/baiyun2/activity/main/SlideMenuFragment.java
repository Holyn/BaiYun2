package com.baiyun2.activity.main;

import com.baiyun2.activity.MyApplication;
import com.baiyun2.activity.R;
import com.baiyun2.constants.Constants;
import com.baiyun2.custom.CircleImageView;
import com.baiyun2.custom.GBlurPic;
import com.baiyun2.http.HttpURL;
import com.baiyun2.sharepreferences.UserInfoSP;
import com.baiyun2.util.ScreenUtil;
import com.baiyun2.vo.parcelable.UserInfoPar;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideMenuFragment extends Fragment{
	public static final int MENU_LOGIN = 1;//用户登陆
	public static final int MENU_TOOLS = 2;//实用工具
	public static final int MENU_SETTING = 3;//设置
	public static final int MENU_HELP = 4;//使用帮助
	public static final int MENU_ABOUT = 5;//关于我们
	public static final int MENU_EXIT = 6;//退出
	private View rootView;
	
	private CircleImageView cvHeader;
	private TextView tvName;
	
	private Bitmap mBitmapIn;//个人信息的背景图片进行高斯模糊
	private Bitmap mBitmapOut;
	private GBlurPic mGBlurPic;
	private float radius = 10;//模糊的半径
	private ImageView ivUserInfoBg;
	
	public OnSlideMenuFragmentEventListener onSlideMenuFragmentEventListener;
	public void setOnSlideMenuFragmentEventListener(
			OnSlideMenuFragmentEventListener onSlideMenuFragmentEventListener) {
		this.onSlideMenuFragmentEventListener = onSlideMenuFragmentEventListener;
	}
	public interface OnSlideMenuFragmentEventListener{
		public void onSlideMenuFragmentEvent(int menuType);
	};
	
	public static SlideMenuFragment newInstance() {
		return new SlideMenuFragment();
	}
	
	public SlideMenuFragment() {
		// TODO Auto-generated constructor stub
	}
	
	//注册广播，接收登录与退出的信息
    private BroadcastReceiver logionReceiver = new BroadcastReceiver() {  
        
        @Override  
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.INTENT_ACTION_LOGIN_SUCCESS)){
            	UserInfoPar userInfoPar = intent.getParcelableExtra(Constants.KEY_USER_INFO_PAR);
            	if (userInfoPar == null) {//退出
            		cvHeader.setImageResource(R.drawable.iv_header_default);
            		tvName.setText("登录");
				}else {//登录
	        		String headerPathLast = userInfoPar.getImg();
	        		if (!TextUtils.isEmpty(headerPathLast)) {
	        			String picUrl = HttpURL.HOST+headerPathLast.substring(1);
	        			System.out.println("====> picUrl = "+picUrl);
	        			ImageLoader.getInstance().displayImage(picUrl, cvHeader);
	        		}
	        		
	        		String name = userInfoPar.getRealName();
	        		if (!TextUtils.isEmpty(name)) {
	        			tvName.setText(name);
	        		}
				}
            }  
        }  
    }; 
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//注册广播，接收登录与退出的信息
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.INTENT_ACTION_LOGIN_SUCCESS);
		getActivity().registerReceiver(logionReceiver, filter);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(logionReceiver);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		int height = ScreenUtil.getScreenHeight(getActivity());
		if (height > 800) {
			rootView = inflater.inflate(R.layout.fragment_slide_menu, container, false);
		}else {
			rootView = inflater.inflate(R.layout.fragment_slide_menu_small, container, false);
		}
		
		//头像
		cvHeader = (CircleImageView)rootView.findViewById(R.id.cv_header);
		//名字
		tvName = (TextView)rootView.findViewById(R.id.tv_name);	
		
		if (((MyApplication)getActivity().getApplication()).isLogin()) {
			//头像
			String headerPathLast = UserInfoSP.getSingleInstance(getActivity()).getImg();
			if (!TextUtils.isEmpty(headerPathLast)) {
				String picUrl = HttpURL.HOST+headerPathLast.substring(1);
//				System.out.println("====> picUrl = "+picUrl);
				ImageLoader.getInstance().displayImage(picUrl, cvHeader);
			}
			//名字
			String name = UserInfoSP.getSingleInstance(getActivity()).getRealName();
			if (!TextUtils.isEmpty(name)) {
				tvName.setText(name);
			}
		}
		
		try {
			mBitmapIn = loadBitmap(R.drawable.slide_user_info_bg);
			mBitmapOut = Bitmap.createBitmap(mBitmapIn.getWidth(),
					mBitmapIn.getHeight(), mBitmapIn.getConfig());
			mGBlurPic = new GBlurPic(getActivity());
			mBitmapOut = mGBlurPic.gBlurBitmap(mBitmapIn, radius);
			ivUserInfoBg = (ImageView)rootView.findViewById(R.id.iv_user_info_bg);
			ivUserInfoBg.setImageBitmap(mBitmapOut);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		RelativeLayout rlInfo = (RelativeLayout)rootView.findViewById(R.id.rl_info);
		rlInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_LOGIN);
			}
		});
		
		RelativeLayout rlTools = (RelativeLayout)rootView.findViewById(R.id.rl_tools);
		rlTools.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_TOOLS);
			}
		});
		
		RelativeLayout rlSetting = (RelativeLayout)rootView.findViewById(R.id.rl_setting);
		rlSetting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_SETTING);
			}
		});
		
		RelativeLayout rlHelp = (RelativeLayout)rootView.findViewById(R.id.rl_help);
		rlHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_HELP);
			}
		});
		
		RelativeLayout rlAbout = (RelativeLayout)rootView.findViewById(R.id.rl_about);
		rlAbout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_ABOUT);
			}
		});
		
		RelativeLayout rlExit = (RelativeLayout)rootView.findViewById(R.id.rl_exit);
		rlExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_EXIT);
			}
		});
		
		
		return rootView;
	}
	
	private Bitmap loadBitmap(int resource) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		return BitmapFactory.decodeResource(getResources(), resource, options);
	}
}
