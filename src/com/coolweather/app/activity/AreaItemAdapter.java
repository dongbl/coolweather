package com.coolweather.app.activity;

import java.util.List;

import com.coolweather.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AreaItemAdapter extends ArrayAdapter<String> {
	private int resourceId;

	public AreaItemAdapter(Context context, 
			int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		String name = getItem(position);		
		view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView textView = (TextView) view.findViewById(R.id.fist_tv);
		textView.setText(name);
		return view;
	}

}
