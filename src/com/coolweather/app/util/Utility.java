package com.coolweather.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.WeatherInfo;
import com.coolweather.app.model.WeatherInfo.Forecast;

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
	
	/*
	 * ����ӷ�������ȡ�����������ݣ�JSON��ʽ��
	 * ����ʵ����
	 * {"weatherinfo":
			{"city":"��ɽ","cityid":"101190404","temp1":"21��","temp2":"9��",
			"weather":"����תС��","img1":"d1.gif","img2":"n7.gif","ptime":"11:00"}
		}
		
   {"desc":"OK","status":1000,
	"data":
	{	"wendu":"22","ganmao":"���������������ˣ������Խ��¹��̣�������ð���ʽϵ͡�",
 		"forecast":[
					{"fengxiang":"�޳�������","fengli":"΢�缶","high":"���� 26��","type":"��","low":"���� 15��","date":"20�����ڶ�"},
					{"fengxiang":"�޳�������","fengli":"΢�缶","high":"���� 24��","type":"����","low":"���� 16��","date":"21��������"},
					{"fengxiang":"�޳�������","fengli":"΢�缶","high":"���� 27��","type":"��","low":"���� 17��","date":"22��������"},
					{"fengxiang":"�޳�������","fengli":"΢�缶","high":"���� 27��","type":"����","low":"���� 19��","date":"23��������"},
					{"fengxiang":"�޳�������","fengli":"΢�缶","high":"���� 28��","type":"��","low":"���� 21��","date":"24��������"}],
	"yesterday":{"fl":"΢��","fx":"�޳�������","high":"���� 25��","type":"��","low":"���� 13��","date":"19������һ"},
	"aqi":"45","city":"����"
	}
  }
	 */
	
	public 	static void handleWeatherResponse(Context context, String content){
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONObject weathInfo = jsonObject.getJSONObject("data");
			JSONArray jsonArray = weathInfo.getJSONArray("forecast");
			String cityName = weathInfo.getString("city");
			Log.d("dongbl", cityName);
			String ganMao = weathInfo.getString("ganmao");
			String wenDu = weathInfo.getString("wendu");
			
		    WeatherInfo wInfo = new WeatherInfo();
		    wInfo.setCurrentWendu(wenDu);
		    wInfo.setGanMao(ganMao);
		    wInfo.setCity(cityName);
		    
		    Forecast[] forecast2 = wInfo.getForecasts();
		    		
			
		    for(int i = 0; i < jsonArray.length(); i++){
		    	String forecastChild = jsonArray.getString(i);
		    	JSONObject jsonObject1 = new JSONObject(forecastChild);
		    	String low = jsonObject1.getString("low").substring(3);
		    	String high = jsonObject1.getString("high").substring(3);
		    	String type = jsonObject1.getString("type");
		    	String date = jsonObject1.getString("date");
		    	String fengXiang = jsonObject1.getString("fengxiang");
		    	String fengLi = jsonObject1.getString("fengli");
		    	forecast2[i].low = low;
		    	forecast2[i].high = high;
		    	forecast2[i].type = type;
		    	forecast2[i].date = date;
		    	forecast2[i].fengXiang = fengXiang;
		    	forecast2[i].fengLi = fengLi;
		    }		
			saveWeatherInfoByObject(context, wInfo, cityName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����������ص��������ݱ��浽SharedPreferences�ļ���
	 * 
	 */
	public static void saveWeatherInfo(Context context, String cityName, 
			String temp1, String temp2, String weatherDesp ){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weatherdesp",weatherDesp);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
		
	}
	
	/**
	 * �����������ص��������ݱ��浽SharedPreferences�ļ���
	 * 
	 */
	public static void saveWeatherInfoByObject(Context context, Object object,String cityName ){
		SharedPreferences preferences = context.getSharedPreferences("Base64", 0);
		//����һ���ֽ�������
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			String string_Base64 = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
			
			Editor editor = preferences.edit();
			editor.putString("object", string_Base64);
			editor.putBoolean("city_selected", true);
			editor.putString("cityName", cityName);
			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Log.d("dongbl", "�������ݵ�SharedPreferences�ɹ�");
	}
	/**
	 * �����������ص��������ݱ��浽SharedPreferences�ļ���
	 * 
	 */
	public static Object getWeatherInfoByObject(Context context, String tagString){
		Object object = null;
		try {
			SharedPreferences preferences = context.getSharedPreferences("Base64", 0);
			String values = preferences.getString(tagString, "");
			
			byte[] base64 = Base64.decode(values.getBytes(),Base64.DEFAULT);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(base64);
			ObjectInputStream ois = new ObjectInputStream(bais);
			object = ois.readObject();
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return object;	
	}
	
}
