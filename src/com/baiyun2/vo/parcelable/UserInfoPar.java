package com.baiyun2.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoPar implements Parcelable {
	private String mobilephone;//手机号码 
	private String xh;//用户名 
	private String emailaddress;//邮件地址
	private String name;//姓名 
	private String classname;//班别 
	private String img; //头像(返回base64编码字符串) 
	private String gender;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mobilephone);
		dest.writeString(xh);
		dest.writeString(emailaddress);
		dest.writeString(name);
		dest.writeString(classname);
		dest.writeString(img);
		dest.writeString(gender);

	}

	public static final Parcelable.Creator<UserInfoPar> CREATOR = new Parcelable.Creator<UserInfoPar>() {
		public UserInfoPar createFromParcel(Parcel in) {
			UserInfoPar par = new UserInfoPar();
			par.mobilephone = in.readString();
			par.xh = in.readString();
			par.emailaddress = in.readString();
			par.name = in.readString();
			par.classname = in.readString();
			par.img = in.readString();
			par.gender = in.readString();
			return par;
		}

		public UserInfoPar[] newArray(int size) {
			return new UserInfoPar[size];
		}
	};

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
