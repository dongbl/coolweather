package com.coolweather.app.activity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.coolweather.app.R;
import com.coolweather.app.model.WeatherInfo;
import com.coolweather.app.model.WeatherInfo.Forecast;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherInfoItemAdapter extends ArrayAdapter<Forecast> {
	private int ResourceId;
	private final LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>(){
		{
			put("«Á", R.drawable.ic_sunny_samll);
			put("∂‡‘∆", R.drawable.ic_cloudy_samll);
			put("¥Û”Í", R.drawable.ic_heavyrain_samll);
			put("¥Û—©", R.drawable.ic_heavysnow_samll);
			put("–°”Í", R.drawable.ic_lightrain_samll);
			put("÷–”Í", R.drawable.ic_moderraterain_samll);
			put("“ı", R.drawable.ic_overcast_samll);
			put("”Íº–—©", R.drawable.ic_rainsnow_samll);
			put("’Û”Í", R.drawable.ic_shower_samll);
			put("¿◊’Û”Í", R.drawable.ic_thundeshower_samll);
			put("–°—©", R.drawable.ic_snow_samll);
			put("±˘±¢", R.drawable.ic_sleet_samll);
			put("¿◊±©’Û", R.drawable.ic_thundeshowehail_samll);
			put("ˆ≤", R.drawable.ic_haze_samll);
			put("ŒÌ", R.drawable.ic_fog_samll);
			put("…≥≥æ±©", R.drawable.ic_sandstorm_samll);
			
			
		}
		
	};

	public WeatherInfoItemAdapter(Context context, int textViewResourceId,
			List<Forecast> objects) {
		super(context, textViewResourceId, objects);
		ResourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		Forecast wInfo = getItem(position);
		view = LayoutInflater.from(getContext()).inflate(ResourceId, null);
		TextView dateTv = (TextView) view.findViewById(R.id.date);
		
		String date = wInfo.date;	
		if(date != null)
		{
			if(date.length() == 6)
			{
				dateTv.setText(date.substring(3));	
			}
			else {
				dateTv.setText(date.substring(2));	
			}
		}
		//TextView despTv = (TextView) view.findViewById(R.id.show_desp);
		//despTv.setText(wInfo.type);
		ImageView despIv = (ImageView) view.findViewById(R.id.show_desp);
		Log.d("dongbl", wInfo.type);
		setWeatherDesp(wInfo.type, despIv);
		
		TextView lowTv = (TextView) view.findViewById(R.id.low);
		lowTv.setText(wInfo.low);	
		TextView highTv = (TextView) view.findViewById(R.id.high);
		highTv.setText(wInfo.high);
		
		return view;
	}
	
	/*
	 * ∏˘æ›÷–Œƒ√Ë ˆ…Ë÷√Õº∆¨
	 */
	private void setWeatherDesp(String value, ImageView imageView){
		if(map.get(value) != null)
		{
			imageView.setImageResource(map.get(value));
		}
		
	}

}
