package com.coolweather.app.model;

import android.R.integer;

/*
 * �м��������
 */
public class City {
	private int id;
	private String name;  //�м�����
	private String code;  //�м�����
	private int provinceId; //ʡ��ID
	
	
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
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
