package com.coolweather.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

/*
 * ����"����|����,����|����"��ʽ������
 */
public class Utility {
	public static Map<String, String> getData(String content){
		if(content == null)
			return null;
		
		Map<String, String> map = new HashMap<String, String>();
			
		String[] strings = content.split(",");
		for(String s: strings){
			String[] innerString = s.split("\\|");
			map.put(innerString[0], innerString[1]);		
		}
			
		return map;
	}
	
	/**
	 * ����ʡ�����ݲ��洢�����ݿ�
	 */
	public synchronized static boolean handleProvincesResponse(
			CoolWeatherDB db,String content){
		if(!TextUtils.isEmpty(content))
		{
			String[] allProvinces = content.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for(String provinces : allProvinces){
					String[] array = provinces.split("\\|");
					Province province = new Province();
					province.setCode(array[0]);
					province.setName(array[1]);
					db.saveProvince(province);
				}
			}
			return true;
		}
		return false;
	}
	
	/*
	 * �����м����ݲ��洢���ݿ�
	 */
	public synchronized static boolean handleCitiesResponse(
			CoolWeatherDB db,String content, int provinceId){
		if(!TextUtils.isEmpty(content))
		{
			String[] allCities = content.split(",");
			if(allCities != null && allCities.length > 0){
				for(String cities : allCities){
					String[] array = cities.split("\\|");
					City city = new City();
					city.setCode(array[0]);
					city.setName(array[1]);
					city.setProvinceId(provinceId);
					db.saveCity(city);
				}
			}
			return true;
		}
		return false;
	}
	
	/*
	 * �����ؼ����ݲ��洢���ݿ�
	 */
	public synchronized static boolean handleCountiesResponse(
			CoolWeatherDB db,String content, int cityId){
		if(!TextUtils.isEmpty(content))
		{
			String[] allCounties = content.split(",");
			if(allCounties != null && allCounties.length > 0){
				for(String counties : allCounties){
					String[] array = counties.split("\\|");
					County county = new County();
					county.setCode(array[0]);
					county.setName(array[1]);
					county.setCityId(cityId);
					db.saveCounty(county);
				}
			}
			return true;
		}
		return false;
	}

}
