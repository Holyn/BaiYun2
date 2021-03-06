package com.baiyun2.fragment.sliding;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun2.activity.MyApplication;
import com.baiyun2.activity.R;
import com.baiyun2.activity.main.MainActivity;
import com.baiyun2.base.BaseFragment;
import com.baiyun2.constants.Constants;
import com.baiyun2.http.HttpURL;
import com.baiyun2.httputils.SlideMenuHttpUtils;
import com.baiyun2.sharepreferences.UserInfoSP;
import com.baiyun2.util.Base64Util;
import com.baiyun2.vo.parcelable.UserInfoPar;
import com.holyn.selectlocalpiclib.LocalImageVo;
import com.holyn.selectlocalpiclib.SelectLocalPicActivity;
import com.holyn.selectlocalpiclib.util.PictureCompressUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserInfoFragment extends BaseFragment{
	public static final int REQUEST_CODE_SELECT_PIC = 0;
	private UserInfoPar userInfoPar;
	private ImageView ivHeader;
	
	public static UserInfoFragment newInstance() {
		return new UserInfoFragment();
	}
	
	public UserInfoFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_user_info;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		userInfoPar = UserInfoSP.getSingleInstance(getActivity()).getUserInfoPar();
		
		//头像
		ivHeader = ((ImageView)rootView.findViewById(R.id.iv_header));
		String header = userInfoPar.getImg();
		if (!TextUtils.isEmpty(header)) {
//			String picUrl = HttpURL.HOST+headerPathLast.substring(1);
//			ImageLoader.getInstance().displayImage(picUrl, ivHeader);

			ivHeader.setImageBitmap(Base64Util.getBitmapFromImage64(header));
		}
		
		((TextView)rootView.findViewById(R.id.tv_realName)).setText(userInfoPar.getName());
//		if (userInfoPar.getGender().equals("1")) {
//			((TextView)rootView.findViewById(R.id.tv_gender)).setText("男");
//		}else if (userInfoPar.getGender().equals("0")) {
//			((TextView)rootView.findViewById(R.id.tv_gender)).setText("女");
//		}
		((TextView)rootView.findViewById(R.id.tv_gender)).setText(userInfoPar.getGender());
		((TextView)rootView.findViewById(R.id.tv_mobile)).setText(userInfoPar.getMobilephone());
		((TextView)rootView.findViewById(R.id.tv_className)).setText(userInfoPar.getClassname());
		((TextView)rootView.findViewById(R.id.tv_account)).setText(userInfoPar.getXh());
		
		((RelativeLayout)rootView.findViewById(R.id.rl_header)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SelectLocalPicActivity.class);
				startActivityForResult(intent, REQUEST_CODE_SELECT_PIC);
			}
		});
		
		((Button)rootView.findViewById(R.id.btn_log_out)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MyApplication)getActivity().getApplication()).setLogin(false);
//				UserInfoSP.getSingleInstance(getActivity()).clear();
				((MainActivity)getActivity()).onBackPressed();
				
				Intent intent = new Intent(Constants.INTENT_ACTION_LOGIN_SUCCESS);
				getActivity().sendBroadcast(intent);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		Toast.makeText(getActivity(), "图片上传暂时不做！", Toast.LENGTH_SHORT).show();
		
//		if (resultCode == Activity.RESULT_OK) {
//			if (requestCode == REQUEST_CODE_SELECT_PIC) {
//				ArrayList<LocalImageVo> list = data.getParcelableArrayListExtra(SelectLocalPicActivity.EXTRA_SELECT_IMAGEVOS);
//				if (list.size() != 0) {
//					showLoadingDialog();
//					LocalImageVo localImageVo = list.get(0);
//					Bitmap bitmap = PictureCompressUtil.getInstance().compress(localImageVo.getPath(), 400, 400, 40);
//					String pic64 = Base64Util.getImage64FromBitmap(bitmap);
//					new SlideMenuHttpUtils(getActivity()).uploadUserImg(userInfoPar.getId(), pic64, new SlideMenuHttpUtils.OnUploadUserImgListener() {
//						
//						@Override
//						public void onUploadUserImg(String imgUrl) {
//							// TODO Auto-generated method stub
//							closeLoadingDialog();
//							if (imgUrl == null) {
//								Toast.makeText(getActivity(), "图片上传失败", Toast.LENGTH_SHORT).show();
//							}else {
//								if (!TextUtils.isEmpty(imgUrl)) {
//									String picUrl = HttpURL.HOST+imgUrl.substring(1);
//									ImageLoader.getInstance().displayImage(picUrl, ivHeader);
//								}
//							}
//						}
//					});
//				}else {
//					Toast.makeText(getActivity(), "请选择图片", Toast.LENGTH_SHORT).show();
//				}
//			}
//		}
		
	}

}
