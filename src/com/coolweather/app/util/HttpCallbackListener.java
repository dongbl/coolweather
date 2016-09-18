package com.coolweather.app.util;

public interface HttpCallbackListener {
	void onRead(String content);
	void onError(Exception e);
}
