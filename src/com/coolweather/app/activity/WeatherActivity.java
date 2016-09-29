package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.R;
import com.coolweather.app.model.WeatherInfo;
import com.coolweather.app.model.WeatherInfo.Forecast;
import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class WeatherActivity extends BaseActivity implements View.OnClickListener {
	private LinearLayout weatherInfoLayout;
	private LinearLayout current_layout;
	private LinearLayout fengliLayout;
	private TextView cityNameTv;     //城市名称
	private TextView weatherDespTv;  //天气描述
	private TextView temp1Tv;		//当日低温
	private TextView temp2Tv;       //当日高温
	private TextView ptimeTv;
	private TextView current_DateTv;  //当日日期
	private TextView currentWenDu;   //当前温度
	
	private TextView fengLiTv; //风力、风向
	private TextView ganMaoTv; //感冒指数
	
	private Button switchBt; //切换城市
	private Button refreshBt; //刷新天气
	
	private String weatherCodeM;  //天气代码
	private String countyCodeM;  //县级代码

	private ImageView weatherIv;   //天气图片
	
	private ListView listView;   //接下来6天天气列表
	
	private ProgressDialog progress;
	private myProgressDialog myProgress;
	
	private ArrayAdapter<Forecast> adapter;
	private List<Forecast> list = new ArrayList<WeatherInfo.Forecast>();
	
	private static final int[] drawableId= {
		
	};
	
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
			fengliLayout.setVisibility(View.INVISIBLE);
			current_layout.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}
		else{
			//showWeather(); //显示天气
			showWeatherByObject();
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
		showProgress();
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
							//showWeather();
							closeProgress();
							showWeatherByObject();
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					public void run() {
						closeProgress();
					}
				});
			}
		});
		
	}
	
	/*
	 * 查询天气代号所对应的天气信息
	 * http://wthrcdn.etouch.cn/weather_mini?city=北京
	 */
	protected void queryWeatherInfo(String weatherCode) {
		String address = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + weatherCode;
		queryFromServer(address, "weatherCode");
	}
	/*
	 * 初始化各个控件
	 */
	private void initWidget(){
		cityNameTv = (TextView) findViewById(R.id.city_name);
		weatherDespTv = (TextView) findViewById(R.id.weather_info);
		current_DateTv = (TextView) findViewById(R.id.current_date);
		currentWenDu = (TextView) findViewById(R.id.current_wendu);
		fengLiTv = (TextView) findViewById(R.id.fengli);
		ganMaoTv = (TextView) findViewById(R.id.ganmao);
		temp1Tv = (TextView) findViewById(R.id.high);
		temp2Tv = (TextView) findViewById(R.id.low);
		switchBt = (Button) findViewById(R.id.switch_bt);
		refreshBt = (Button) findViewById(R.id.refresh_bt);
		listView = (ListView) findViewById(R.id.listview);
		adapter = new WeatherInfoItemAdapter(WeatherActivity.this, R.layout.weather_info_item, list);
		listView.setAdapter(adapter);
		switchBt.setOnClickListener(this);
		refreshBt.setOnClickListener(this);
		
		current_layout = (LinearLayout) findViewById(R.id.current_layout);
		fengliLayout = (LinearLayout) findViewById(R.id.fengli_layout);
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
		//weatherDespTv.setText(spfs.getString("weatherdesp", ""));
		String s = spfs.getString("weatherdesp", "");
		
		
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
	
	/*
	 * 通过对象获取保存的天气信息
	 */
	
	private void showWeatherByObject(){
		current_layout.setVisibility(View.VISIBLE);
		fengliLayout.setVisibility(View.VISIBLE);
		list.clear();
		WeatherInfo wInfo = (WeatherInfo) Utility.getWeatherInfoByObject(WeatherActivity.this, "object");
		Forecast[] forecasts = wInfo.getForecasts();
		for(int i = 1; i < 5; i++){
			Log.d("dongbl", forecasts[i].date);
			list.add(forecasts[i]);
		}
		String date = forecasts[0].date;
		if(date.length() == 6)
		{
			current_DateTv.setText(date.substring(3));	
		}else {
			current_DateTv.setText(date.substring(2));	
		}
		cityNameTv.setText(wInfo.getCity());
		ganMaoTv.setText("感冒指数："+wInfo.getGanMao());
		
		temp1Tv.setText(forecasts[0].low);
		temp2Tv.setText(forecasts[0].high);
		String desp = forecasts[0].type;
		Log.d("dongbl", desp);
		weatherDespTv.setText(desp);
		adapter.notifyDataSetChanged();
		
		String fengString = "风向风力："+forecasts[0].fengXiang + " ; "+forecasts[0].fengLi;
		fengLiTv.setText(fengString);
		
		currentWenDu.setText(wInfo.getCurrentWendu());
		
		
		//天气更新更新成功后，启动后台服务
		Intent intent = new Intent(this, AutoUpdateReceiver.class);
		startService(intent);
	}
	
	/*
	 * 更新天气进度
	 */
	private void showProgress(){
		if(myProgress == null){
			myProgress = myProgressDialog.createDialog(this);
			myProgress.setMessage("正在努力更新最新天气......");
			
		}
		myProgress.show();
	}
	
	/*
	 * 关闭进度
	 */
	private void closeProgress(){
		if(myProgress != null){
			myProgress.dismiss();
		}
		
	}
	
}
