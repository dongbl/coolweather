package com.coolweather.app.activity;

import com.coolweather.app.R;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends BaseActivity implements View.OnClickListener {
	private LinearLayout weatherInfoLayout;
	private TextView cityNameTv;
	private TextView weatherDespTv;
	private TextView temp1Tv;
	private TextView temp2Tv;
	private TextView ptimeTv;
	private TextView current_DateTv;
	
	private Button switchBt; //切换城市
	private Button refreshBt; //刷新天气
	
	private String weatherCodeM;  //天气代码
	private String countyCodeM;  //县级代码

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		/*
		 * 初始化控件
		 */
		initWidget();
		
		String countyCode = getIntent().getStringExtra("county_code");
		
		if(!TextUtils.isEmpty(countyCode))
		{	
			countyCodeM = countyCode;
			ptimeTv.setText("正在同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameTv.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}
		else{
			showWeather(); //显示天气
		}
	}
	/*
	 * 查询县代号所对应的天气代号
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
							weatherCodeM = weatherCode;
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
						ptimeTv.setText("同步失败...");
					}
				});
			}
		});
		
	}
	
	/*
	 * 查询天气代号所对应的天气信息
	 */
	protected void queryWeatherInfo(String weatherCode) {
		String address = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + weatherCode;
		queryFromServer(address, "weatherCode");
	}
	/*
	 * 初始化各个控件
	 */
	private void initWidget(){
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameTv = (TextView) findViewById(R.id.city_name);
		weatherDespTv = (TextView) findViewById(R.id.weather_desp);
		temp1Tv = (TextView) findViewById(R.id.temp1);
		temp2Tv = (TextView) findViewById(R.id.temp2);
		ptimeTv = (TextView) findViewById(R.id.publish_text);
		current_DateTv = (TextView) findViewById(R.id.current_date);
		switchBt = (Button) findViewById(R.id.switch_bt);
		refreshBt = (Button) findViewById(R.id.refresh_bt);
		switchBt.setOnClickListener(this);
		refreshBt.setOnClickListener(this);
	}
	/*
	 * 显示天气信息
	 */
	private void showWeather(){
		SharedPreferences spfs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
		cityNameTv.setText(spfs.getString("city_name", " "));
		temp1Tv.setText(spfs.getString("temp1", ""));
		temp2Tv.setText(spfs.getString("temp2", ""));
		ptimeTv.setText("今天" + spfs.getString("ptime", "") + "发布");
		current_DateTv.setText(spfs.getString("current_date", ""));
		weatherDespTv.setText(spfs.getString("weatherdesp", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameTv.setVisibility(View.VISIBLE);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_bt:  //切换城市
				Intent intent = new Intent(WeatherActivity.this, ChooseAreaActivity.class);
				intent.putExtra("from_weatherActivity",true);
				startActivity(intent);
				finish();
			break;
		case R.id.refresh_bt://刷新天气
			ptimeTv.setText("正在刷新...");
			Log.v("dongbl", weatherCodeM);
			if(!TextUtils.isEmpty(weatherCodeM)){
				queryWeatherInfo(weatherCodeM);
			}
			
			break;

		default:
			break;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, ChooseAreaActivity.class);
		intent.putExtra("from_weatherActivity", true);
		intent.putExtra("countyCode", countyCodeM);
		startActivity(intent);
		finish();
	}
	
}
