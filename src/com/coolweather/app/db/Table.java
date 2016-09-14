package com.coolweather.app.db;

public class Table {
	/*
	 * table province:省级表
	 * province_name：省级名称
	 * province_code：省级代码
	 */
	public static final String TABLE_PROVINCE = "create table province(" +
			"id integer primary key autoincrement," +
			"province_name text," +
			"province_code text)";
	/*
	 * table province:市级表
	 * province_name：市级名称
	 * province_code：市级代码
	 */
	public static final String TABLE_CITY = "create table city(" +
			"id integer primary key autoincrement," +
			"city_name text," +
			"city_code text," +
			"province_id integer)";
	
	/*
	 * table province:县级表
	 * province_name：县级名称
	 * province_code：县级代码
	 */
	public static final String TABLE_COUNTY = "create table county(" +
			"id integer primary key autoincrement," +
			"county_name text," +
			"county_code text," +
			"city_id integer)";
	
}
