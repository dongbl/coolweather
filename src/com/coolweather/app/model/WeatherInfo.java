package com.coolweather.app.model;

import java.io.Serializable;
import java.util.Arrays;

import android.R.integer;

/*
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
public class WeatherInfo implements Serializable {
	private String currentWendu;  //��ǰ�¶�
	private String ganMao;        //��ðָ��
	private String city;          //����
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
