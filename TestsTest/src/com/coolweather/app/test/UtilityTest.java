package com.coolweather.app.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.test.AndroidTestCase;
import android.util.Log;

public class UtilityTest extends AndroidTestCase {
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testUtility(){
		String string = "01|北京,02|上海,03|广州";
		
		Map<String, String> map = new HashMap<String, String>();
		map = Utility.getData(string);
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String id = iterator.next();
			Log.d("dongbl", id);
			Log.d("dongbl", map.get(id));
		}
		
	}
	
	public void testUtility2(){
		String address = "http://www.weather.com.cn/data/cityinfo/101190404.html";
		String address1 = "http://wthrcdn.etouch.cn/weather_mini?citykey=101010100";
		HttpUtil.connect2Server(address1, new HttpCallbackListener() {
			
			@Override
			public void onRead(String content) {
				try {
					JSONObject jsonObject = new JSONObject(content);
					JSONObject weathInfo = jsonObject.getJSONObject("data");
					JSONArray forecast = weathInfo.getJSONArray("forecast");
					String cityName = weathInfo.getString("city");
					String forecast1 = forecast.getString(0);
					JSONObject jsonObject1 = new JSONObject(forecast1);
					String temp1 = jsonObject1.getString("high").substring(3);
					String temp2 = jsonObject1.getString("low");
					String weatherDesp = jsonObject1.getString("type");
					Log.d("dongbl", cityName+"  "+temp1+"  "+temp2+"  "+weatherDesp);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onError(Exception e) {
				e.printStackTrace();
				Log.d("dongbl", "111111111111");
				
			}
		});
	}

}
