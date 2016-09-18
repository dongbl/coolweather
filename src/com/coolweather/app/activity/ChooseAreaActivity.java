package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.R;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.R.anim;
import android.R.integer;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*
 * 实现选择地区界面
 */
public class ChooseAreaActivity extends BaseActivity {
	private static final int PROVINCE_LEVEL = 0;
	private static final int CITY_LEVEL = 1;
	private static final int COUNTY_LEVEL = 2;
	private TextView titleTv;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private List<String> dataList = new ArrayList<String>();
	private CoolWeatherDB db;
	private List<Province> provincesList;
	private List<City> cityList;
	private List<County> countyList;
	
	private Province selectProvince;
	private City selectcCity;
	private County selectcCounty;
	
	private int currentLevel;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_area);
		
		titleTv = (TextView)findViewById(R.id.title_text);
		listView = (ListView)findViewById(R.id.list_view);
		
		adapter = new ArrayAdapter<String>(ChooseAreaActivity.this, android.R.layout.simple_list_item_1,dataList);
		listView.setAdapter(adapter);
		db = CoolWeatherDB.newInstance(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(currentLevel == PROVINCE_LEVEL){
					selectProvince = provincesList.get(arg2);
					queryCities();
				}
				else if(currentLevel == CITY_LEVEL){
					selectcCity = cityList.get(arg2);
					queryCounties();
				}
			}
		});
		
		queryProvinces(); //加载省级数据
	}
	/*
	 * 查询所有省级数据
	 */
	
	private void queryProvinces() {
		provincesList = db.loadPronvince();
		if(provincesList.size() > 0){
			dataList.clear();
			for(Province province:provincesList){
				dataList.add(province.getName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleTv.setText("中国");
			currentLevel = PROVINCE_LEVEL;
		}
		else {
			queryFromServer(null, "province");
		}
	}

	/**
	 * 从服务器上获取数据
	 */
	private void queryFromServer(final String code, final String type) {
		String address;
		if(TextUtils.isEmpty(code)){
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		else {
			address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
		}
		showProgressDialog();
		HttpUtil.connect2Server(address, new HttpCallbackListener() {
			
			@Override
			public void onRead(String content) {
				boolean result = false;
				if("province".equals(type)){
					result = Utility.handleProvincesResponse(db, content);
				}
				if("city".equals(type)){
					result = Utility.handleCitiesResponse(db, content, selectProvince.getId());
				}
				if("county".equals(type)){
					result = Utility.handleCountiesResponse(db, content, selectcCity.getId());
				}
				if(result){
					//回到主线程
					runOnUiThread(new Runnable() {
						public void run() {
							closeProgressDialog();
							if("province".equals(type))
								queryProvinces();
							if("city".equals(type))
								queryCities();
							if("county".equals(type))
							{
								queryCounties();
							}
						}
					});
				}
				
			}
			
			@Override
			public void onError(Exception e) {
				Log.d("dongbl", "失败");
				
			}
		});
	}

	/**
	 * 查询所有县级数据信息
	 */
	protected void queryCounties() {
		countyList = db.loadCounties(selectcCity.getId());
		if(countyList.size() > 0){
			dataList.clear();
			for(County county : countyList){
				dataList.add(county.getName());
			}
			
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleTv.setText(selectcCity.getName());
			currentLevel = COUNTY_LEVEL;
		}
		else
		{
			queryFromServer(selectcCity.getCode(), "county");
		}
	}
	/*
	 * 查询所有市级数据信息
	 */
	protected void queryCities() {
		cityList = db.loadCities(selectProvince.getId());
		if(cityList.size() > 0){
			dataList.clear();
			for(City city:cityList){
				dataList.add(city.getName());
			}
			
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleTv.setText(selectProvince.getName());
			currentLevel = CITY_LEVEL;
		}
		else{
			queryFromServer(selectProvince.getCode(), "city");
		}
		
	}
	
	/*
	 * 显示进度对话框
	 */
	private void showProgressDialog(){
		if(progressDialog == null){
			 progressDialog = new ProgressDialog(this);
			 progressDialog.setMessage("正在加载...");
			 progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	
	/*
	 * 关闭进度对话框
	 */
	private void closeProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
	
	@Override
	public void onBackPressed() {
		if(currentLevel == CITY_LEVEL)
		{
			queryProvinces();
		}
		else if(currentLevel == COUNTY_LEVEL){
			queryCities();
		}
	}
}
