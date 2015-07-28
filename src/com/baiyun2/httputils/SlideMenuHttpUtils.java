package com.baiyun2.httputils;

import android.content.Context;
import android.widget.Toast;

import com.baiyun2.http.HttpRecode;
import com.baiyun2.http.HttpURL;
import com.baiyun2.vo.parcelable.VersionPar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class SlideMenuHttpUtils extends HttpUtils{
	
	private Context context;
	
	public SlideMenuHttpUtils(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public interface OnGetVersionListener{//获取版本信息
		public void onGetVersion(VersionPar versionPar);
	}
	
	public interface OnPostBaiduPushListener {// 导入百度推送所需的mobileChannelId和mobileUserId接口
		public void onPostBaiduPush(boolean isSuccess);
	}

//	public interface OnPostLoginListener {//登录
//		public void onPostLogin(UserInfoPar userInfoPar);
//	}
//	
//	public interface OnUploadUserImgListener{//上传头像
//		public void onUploadUserImg(String imgUrl);
//	}
//	
//	public interface OnPostAdviceListener{//意见反馈
//		public void onPostAdvice(boolean isSuccess);
//	}
	
	public void getVersion(String version, final OnGetVersionListener onGetVersionListener){
		send(HttpMethod.GET, HttpURL.R_VERSION+version, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				VersionPar versionPar = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.GET_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								versionPar = new Gson().fromJson(dataEle, VersionPar.class);
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "服务器无数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					versionPar = null;
					System.out.println(e);
				}
				onGetVersionListener.onGetVersion(versionPar);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetVersionListener.onGetVersion(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	


	public void postBaiduPush(String mobileUserId, String mobileChannelId, final OnPostBaiduPushListener baiduPushListener) {
		final RequestParams params = new RequestParams();
		params.addBodyParameter(HttpURL.PARAM_MOBILE_USER_ID, mobileUserId);
		params.addBodyParameter(HttpURL.PARAM_MOBILE_CHANNEL_ID, mobileChannelId);

		send(HttpMethod.POST, HttpURL.R_SET_PUSH, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				boolean isSuccess = false;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.INSERT_SUCCESS)) {
							System.out.println("====> 成功导入百度推送所需的两个id");
							isSuccess = true;
						}
					}
				} catch (Exception e) {
					isSuccess = false;
					System.out.println(e);
				}
				if (baiduPushListener != null) {
					baiduPushListener.onPostBaiduPush(isSuccess);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				if (baiduPushListener != null) {
					baiduPushListener.onPostBaiduPush(false);
				}
			}

		});
	}
}