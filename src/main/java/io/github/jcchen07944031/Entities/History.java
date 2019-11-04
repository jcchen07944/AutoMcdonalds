package io.github.jcchen07944031.Entities;

import java.io.Serializable;

public class History implements Serializable {
	
	private String couponID;
	private String redeemEndDateTime;
	private String title;
	private String imgUrl;
	private String type;

	public History() {
		
	}

	public void setCouponID(String couponID) {
		this.couponID = couponID;
	}

	public String getCouponID() {
		return couponID;
	}

	public void setRedeemEndDateTime(String redeemEndDateTime) {
		this.redeemEndDateTime = redeemEndDateTime;
	}

	public String getRedeemEndDateTime() {
		return redeemEndDateTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
