package com.coolweather.app.activity;

import com.coolweather.app.R;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends BaseActivity {
	private LinearLayout weatherInfoLayout;
	private TextView cityNameTv;
	private TextView weatherDespTv;
	private TextView temp1Tv;
	private TextView temp2Tv;
	private TextView ptimeTv;
	private TextView current_DateTv;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		/*
		 * ��ʼ���ؼ�
		 */
		initWidget();
		
		String countyCode = getIntent().getStringExtra("county_code");
		if(!TextUtils.isEmpty(countyCode))
		{
			ptimeTv.setText("����ͬ����...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameTv.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}
		else{
			showWeather(); //��ʾ����
		}
	}
	/*
	 * ��ѯ�ش�������Ӧ����������
	 */
	private void queryWeatherCode(String countyCode) {
		String address = "http://www.weather.com.cn/data/list3/city" +
				countyCode + ".xml";
		queryFromServer(address, "countyCode");
	}
	private void queryFromServer(String address, final String type) {
		HttpUtil.connect2Server(address, new HttpCallbackListener() {
			
			@Override
			public void onRead(String content) {
				if("countyCode".equals(type)){
					if(!TextUtils.isEmpty(content)){
						String[] array = content.split("\\|");
						if(array.length == 2){
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}
				else if("weatherCode".equals(type)){
					Utility.handleWeatherResponse(WeatherActivity.this, content);
					runOnUiThread(new Runnable() {
						public void run() {
							showWeather();
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {
				Log.d("dongbl", "11111111");
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					public void run() {
						ptimeTv.setText("ͬ��ʧ��...");
					}
				});
			}
		});
		
	}
	
	/*
	 * ��ѯ������������Ӧ��������Ϣ
	 */
	protected void queryWeatherInfo(String weatherCode) {
		String address = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + weatherCode;
		queryFromServer(address, "weatherCode");
	}
	/*
	 * ��ʼ�������ؼ�
	 */
	private void initWidget(){
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameTv = (TextView) findViewById(R.id.city_name);
		weatherDespTv = (TextView) findViewById(R.id.weather_desp);
		temp1Tv = (TextView) findViewById(R.id.temp1);
		temp2Tv = (TextView) findViewById(R.id.temp2);
		ptimeTv = (TextView) findViewById(R.id.publish_text);
		current_DateTv = (TextView) findViewById(R.id.current_date);
	}
	/*
	 * ��ʾ������Ϣ
	 */
	private void showWeather(){
		SharedPreferences spfs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
		cityNameTv.setText(spfs.getString("city_name", " "));
		temp1Tv.setText(spfs.getString("temp1", ""));
		temp2Tv.setText(spfs.getString("temp2", ""));
		ptimeTv.setText("����" + spfs.getString("ptime", "") + "����");
		current_DateTv.setText(spfs.getString("current_date", ""));
		weatherDespTv.setText(spfs.getString("weatherdesp", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameTv.setVisibility(View.VISIBLE);
	}
	
}
