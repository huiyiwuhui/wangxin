package com.yhfund.app.entities.aboutUs;

import java.util.Date;

public class AboutUs {
	
	private int id;
	
	
	private String message;
	
	
	private String imgUrl;
	
	
	private Date time;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
