package com.coolweather.app.model;

/*
 * 省级相关数据
 */
public class Province {
	private int id;
	private String name;  //省级名称
	private String code;  //省级代码
	
	
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
