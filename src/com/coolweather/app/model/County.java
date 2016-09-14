package com.coolweather.app.model;

import android.R.integer;

/*
 * 县级相关数据
 */
public class County {
	private int id;
	private String name;  //县级名称
	private String code;  //县级代码
	private int cityId;   //市级ID
	
	
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
