package com.coolweather.app.model;

import android.R.integer;

/*
 * �ؼ��������
 */
public class County {
	private int id;
	private String name;  //�ؼ�����
	private String code;  //�ؼ�����
	private int cityId;   //�м�ID
	
	
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