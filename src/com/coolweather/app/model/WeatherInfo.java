package com.coolweather.app.model;

import java.io.Serializable;
import java.util.Arrays;

import android.R.integer;

/*
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
public class WeatherInfo implements Serializable {
	private String currentWendu;  //当前温度
	private String ganMao;        //感冒指数
	private String city;          //城市
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private Forecast[] forecasts;
	
	public class Forecast implements Serializable{
		public String fengXiang;
		public String fengLi;
		public String high;
		public String type;
		public String low;
		public String date;
		
	}
	
	public WeatherInfo(){
		forecasts = new Forecast[7+1];
		for(int i = 0; i < forecasts.length; i++)
			forecasts[i] = new Forecast();
	}

	public String getCurrentWendu() {
		return currentWendu;
	}

	public void setCurrentWendu(String currentWendu) {
		this.currentWendu = currentWendu;
	}

	public String getGanMao() {
		return ganMao;
	}

	public void setGanMao(String ganMao) {
		this.ganMao = ganMao;
	}

	public Forecast[] getForecasts() {
		return forecasts;
	}

	public void setForecasts(Forecast[] forecasts) {
		this.forecasts = forecasts;
	}

	
}
