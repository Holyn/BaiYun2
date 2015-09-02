package com.baiyun2.sharepreferences;

import com.baiyun2.constants.Constants;
import com.baiyun2.vo.parcelable.UserInfoPar;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 使用SharePreference存储一些app的设置信息,xml的文件名为APP_SETTING = "appSetting"
 * 
 * @author Holyn
 * @create 2015-1-18
 * @modified
 */
public class UserInfoSP {
	public static UserInfoSP singleInstance;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public UserInfoSP(Context context) {
		sp = context.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static synchronized UserInfoSP getSingleInstance(Context context) {
		if (singleInstance == null) {
			singleInstance = new UserInfoSP(context);
		}
		return singleInstance;
	}
	
	//清空数据
	public void clear() {
		editor.clear();
		editor.commit();
	}
	
	public void setUserInfoPar(UserInfoPar userInfoPar) {
		// TODO Auto-generated method stub
//		setId(userInfoPar.getId());
		setMobilephone(userInfoPar.getMobilephone());
		setXh(userInfoPar.getXh());
		setEmailaddress(userInfoPar.getEmailaddress());
		setName(userInfoPar.getName());
		setClassname(userInfoPar.getClassname());
		setImg(userInfoPar.getImg());
		setGender(userInfoPar.getGender());
	}
	
	public UserInfoPar getUserInfoPar() {
		UserInfoPar userInfoPar = new UserInfoPar();
//		userInfoPar.setId(getId());
		userInfoPar.setMobilephone(getMobilephone());
		userInfoPar.setXh(getXh());
		userInfoPar.setEmailaddress(getEmailaddress());
		userInfoPar.setName(getName());
		userInfoPar.setClassname(getClassname());
		userInfoPar.setImg(getImg());
		userInfoPar.setGender(getGender());
		return userInfoPar;
	}

	//id
	public void setId(String id) {
		editor.putString("id", id);
		editor.commit();
	}

	public String getId() {
		return sp.getString("id", "");
	}

	//mobilephone 手机号码 
	public void setMobilephone(String mobilephone) {
		editor.putString("mobilephone", mobilephone);
		editor.commit();
	}

	public String getMobilephone() {
		return sp.getString("mobilephone", "");
	}
	
	//xh 用户名 
	public void setXh(String xh) {
		editor.putString("xh", xh);
		editor.commit();
	}

	public String getXh() {
		return sp.getString("xh", "");
	}
	
	//emailaddress 邮件地址 
	public void setEmailaddress(String emailaddress) {
		editor.putString("emailaddress", emailaddress);
		editor.commit();
	}

	public String getEmailaddress() {
		return sp.getString("emailaddress", "");
	}
	
	//name 姓名 
	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}

	public String getName() {
		return sp.getString("name", "");
	}
	
	//classname 班别 
	public void setClassname(String className) {
		editor.putString("classname", className);
		editor.commit();
	}

	public String getClassname() {
		return sp.getString("classname", "");
	}
	
	//img 头像(返回base64编码字符串)
	public void setImg(String img) {
		editor.putString("img", img);
		editor.commit();
	}

	public String getImg() {
		return sp.getString("img", "");
	}
	
	//gender 性别 
	public void setGender(String gender) {
		editor.putString("gender", gender);
		editor.commit();
	}

	public String getGender() {
		return sp.getString("gender", "");
	}
	
	//userName
	public void setUserName(String userName) {
		editor.putString("userName", userName);
		editor.commit();
	}

	public String getUserName() {
		return sp.getString("userName", "");
	}
	
	//password
	public void setPassword(String password) {
		editor.putString("password", password);
		editor.commit();
	}

	public String getPassword() {
		return sp.getString("password", "");
	}
	
	//mobileUserId 百度推送
	public void setMobileUserId(String mobileUserId) {
		editor.putString("mobileUserId", mobileUserId);
		editor.commit();
	}

	public String getMobileUserId() {
		return sp.getString("mobileUserId", "");
	}
	
	//mobileChannelId 百度推送
	public void setMobileChannelId(String mobileChannelId) {
		editor.putString("mobileChannelId", mobileChannelId);
		editor.commit();
	}

	public String getMobileChannelId() {
		return sp.getString("mobileChannelId", "");
	}
	
}
