package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

/*
 * 解析"代号|城市,代号|城市"格式的数据
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
	 * 处理省级数据并存储到数据库
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
	 * 处理市级数据并存储数据库
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
	 * 处理县级数据并存储数据库
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
	
	/*
	 * 处理从服务器获取到的天气数据，JSON格式的
	 * 数据实例：
	 * {"weatherinfo":
			{"city":"昆山","cityid":"101190404","temp1":"21℃","temp2":"9℃",
			"weather":"多云转小雨","img1":"d1.gif","img2":"n7.gif","ptime":"11:00"}
		}
		
   {"desc":"OK","status":1000,
	"data":
	{	"wendu":"22","ganmao":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。",
 		"forecast":[
					{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 26℃","type":"晴","low":"低温 15℃","date":"20日星期二"},
					{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 24℃","type":"多云","low":"低温 16℃","date":"21日星期三"},
					{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 27℃","type":"阴","low":"低温 17℃","date":"22日星期四"},
					{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 27℃","type":"多云","low":"低温 19℃","date":"23日星期五"},
					{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 28℃","type":"阴","low":"低温 21℃","date":"24日星期六"}],
	"yesterday":{"fl":"微风","fx":"无持续风向","high":"高温 25℃","type":"晴","low":"低温 13℃","date":"19日星期一"},
	"aqi":"45","city":"北京"
	}
  }
	 */
	
	public 	static void handleWeatherResponse(Context context, String content){
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONObject weathInfo = jsonObject.getJSONObject("data");
			JSONArray forecast = weathInfo.getJSONArray("forecast");
			String cityName = weathInfo.getString("city");
			String forecastChild = forecast.getString(0);
			JSONObject jsonObject1 = new JSONObject(forecastChild);
			String temp1 = jsonObject1.getString("low").substring(3);
			String temp2 = jsonObject1.getString("high").substring(3);
			String weatherDesp = jsonObject1.getString("type");
			
			saveWeatherInfo(context, cityName, temp1, temp2, weatherDesp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将服务器返回的天气数据保存到SharedPreferences文件中
	 * 
	 */
	public static void saveWeatherInfo(Context context, String cityName, 
			String temp1, String temp2, String weatherDesp ){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weatherdesp",weatherDesp);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
		
	}
}
