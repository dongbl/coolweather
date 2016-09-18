package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	/*
	 * ���ݿ����
	 */
	private static final String DATABASE_NAME = "cool_weather";
	
	/*���ݿ�汾*/
	private static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/*
	 * ˽�й��캯����ʹ��ȫ��ֻ��һ�����ݿ����ʵ��
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = 
				new CoolWeatherOpenHelper(context, DATABASE_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();		
	}
	
	/*�����ͬ����������Ϊthis*/
	public synchronized static CoolWeatherDB newInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * ����ʡ�����ݵ����ݿ���
	 */
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getName());
			values.put("province_code", province.getCode());
			db.insert("province", null, values );
		}
	}
	
	/**
	 * �����м����ݵ����ݿ���
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getName());
			values.put("city_code", city.getCode());
			values.put("province_id", city.getProvinceId());
			db.insert("city", null, values );
		}
	}
	
	/**
	 * �����ؼ����ݵ����ݿ���
	 */
	public void saveCounty(County county){
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getName());
			values.put("county_code", county.getCode());
			values.put("city_id", county.getCityId());
			db.insert("county", null, values );
		}
	}
	
	/*
	 * ��ȡȫ��ʡ����Ϣ
	 */
	public List<Province> loadPronvince(){
		List<Province> list = new ArrayList<Province>();
		String orderBy = "id";
		Cursor cursor = db.query("province", null, null, null, null, null, orderBy);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("province_name"));
			String code = cursor.getString(cursor.getColumnIndex("province_code"));
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			Province province = new Province();
			province.setCode(code);
			province.setName(name);
			province.setId(id);
			list.add(province);
		}
		return list;
	}
	
	/*
	 * ��ȡȫ���м���Ϣ
	 */
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		String orderBy = "id";
		String selection = "province_id = ?";
		String[] selectionArgs = {String.valueOf(provinceId)};
		Cursor cursor = db.query("city", null, selection, selectionArgs, null, null, orderBy);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("city_name"));
			String code = cursor.getString(cursor.getColumnIndex("city_code"));
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			City city = new City();
			city.setCode(code);
			city.setName(name);
			city.setId(id);
			list.add(city);
		}
		return list;
	}
	

	/*
	 * �����м�ID��ȡ�����µ������ؼ�����
	 */
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		String orderBy = "id";
		String selection = "city_id = ?";
		String[] selectionArgs = {String.valueOf(cityId)};
		Cursor cursor = db.query("county", null, selection, selectionArgs, null, null, orderBy);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("county_name"));
			String code = cursor.getString(cursor.getColumnIndex("county_code"));
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			County county = new County();
			county.setCode(code);
			county.setName(name);
			county.setId(id);
			list.add(county);
		}
		return list;
	}
	
}
