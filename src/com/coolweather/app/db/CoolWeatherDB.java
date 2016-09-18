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
	 * 数据库表明
	 */
	private static final String DATABASE_NAME = "cool_weather";
	
	/*数据库版本*/
	private static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/*
	 * 私有构造函数，使得全局只有一个数据库操作实例
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = 
				new CoolWeatherOpenHelper(context, DATABASE_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();		
	}
	
	/*定义成同步方法，锁为this*/
	public synchronized static CoolWeatherDB newInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 保存省级数据到数据库中
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
	 * 保存市级数据到数据库中
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
	 * 保存县级数据到数据库中
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
	 * 获取全国省级信息
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
	 * 获取全国市级信息
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
	 * 根据市级ID获取该市下的所有县级城市
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
