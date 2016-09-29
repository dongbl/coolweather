package com.coolweather.app.activity;

import com.coolweather.app.R;
import com.coolweather.app.util.Utility;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

public class myProgressDialog extends Dialog {
	private Context context = null;
	private static myProgressDialog mDialog = null;

	public myProgressDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	public myProgressDialog(Context context, int theme) {
		super(context,theme);
	}
	
	public static myProgressDialog createDialog(Context context){
		mDialog = new myProgressDialog(context, R.style.SF_pressDialogCustom);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setContentView(R.layout.progressdialog);
		mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return mDialog;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(null == mDialog){
			return;
		}
		ImageView loadingImageView = (ImageView) mDialog.findViewById(R.id.progress_iv);
		AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView.getBackground();
		animationDrawable.start();
	}
	
	public myProgressDialog setMessage(String msg){
		TextView msgTextView = (TextView) mDialog.findViewById(R.id.progress_tv);
		if(!TextUtils.isEmpty(msg)){
			msgTextView.setText(msg);
		}
	
		return mDialog;
	}
}
