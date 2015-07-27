package com.baiyun2.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class RecruitPar implements Parcelable {
	private String id;
	private String name;
	private String type;
	private String leaf;
	
	private String img;
	private String creater;
	private String sortOrder;
	private String updateUserName;
	
	private String contentType;
	private String expanded;
	private String menuId;
	private String allowEdit;
	
	private String allowDelete;
	private String enabled;
	private String rootId;
	private String bRecruitContentViewList;
	
	private String recruitInforServiceUrl;
	private String createTime;
	private String updateTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(type);
		dest.writeString(leaf);
		
		dest.writeString(img);
		dest.writeString(creater);
		dest.writeString(sortOrder);
		dest.writeString(updateUserName);
		
		dest.writeString(contentType);
		dest.writeString(expanded);
		dest.writeString(menuId);
		dest.writeString(allowEdit);
		
		dest.writeString(allowDelete);
		dest.writeString(enabled);
		dest.writeString(rootId);
		dest.writeString(bRecruitContentViewList);

		dest.writeString(recruitInforServiceUrl);
		dest.writeString(createTime);
		dest.writeString(updateTime);
	}

	public static final Parcelable.Creator<RecruitPar> CREATOR = new Parcelable.Creator<RecruitPar>() {
		public RecruitPar createFromParcel(Parcel in) {
			RecruitPar par = new RecruitPar();
			par.id = in.readString();
			par.name = in.readString();
			par.type = in.readString();
			par.leaf = in.readString();
			
			par.img = in.readString();
			par.creater = in.readString();
			par.sortOrder = in.readString();
			par.updateUserName = in.readString();
			
			par.contentType = in.readString();
			par.expanded = in.readString();
			par.menuId = in.readString();
			par.allowEdit = in.readString();
			
			par.allowDelete = in.readString();
			par.enabled = in.readString();
			par.rootId = in.readString();
			par.bRecruitContentViewList = in.readString();

			par.recruitInforServiceUrl = in.readString();
			par.createTime = in.readString();
			par.updateTime = in.readString();
			
			return par;
		}

		public RecruitPar[] newArray(int size) {
			return new RecruitPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getAllowEdit() {
		return allowEdit;
	}

	public void setAllowEdit(String allowEdit) {
		this.allowEdit = allowEdit;
	}

	public String getAllowDelete() {
		return allowDelete;
	}

	public void setAllowDelete(String allowDelete) {
		this.allowDelete = allowDelete;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getbRecruitContentViewList() {
		return bRecruitContentViewList;
	}

	public void setbRecruitContentViewList(String bRecruitContentViewList) {
		this.bRecruitContentViewList = bRecruitContentViewList;
	}

	public String getRecruitInforServiceUrl() {
		return recruitInforServiceUrl;
	}

	public void setRecruitInforServiceUrl(String recruitInforServiceUrl) {
		this.recruitInforServiceUrl = recruitInforServiceUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
