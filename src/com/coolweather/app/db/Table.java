package com.coolweather.app.db;

public class Table {
	/*
	 * table province:ʡ����
	 * province_name��ʡ������
	 * province_code��ʡ������
	 */
	public static final String TABLE_PROVINCE = "create table province(" +
			"id integer primary key autoincrement," +
			"province_name text," +
			"province_code text)";
	/*
	 * table province:�м���
	 * province_name���м�����
	 * province_code���м�����
	 */
	public static final String TABLE_CITY = "create table city(" +
			"id integer primary key autoincrement," +
			"city_name text," +
			"city_code text," +
			"province_id integer)";
	
	/*
	 * table province:�ؼ���
	 * province_name���ؼ�����
	 * province_code���ؼ�����
	 */
	public static final String TABLE_COUNTY = "create table county(" +
			"id integer primary key autoincrement," +
			"county_name text," +
			"county_code text," +
			"city_id integer)";
	
}
