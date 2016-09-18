package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;

public class HttpUtil {
	public static void connect2Server(final String address, 
			final HttpCallbackListener listener){
		/**
		 * ���̵߳����ݲ���ͨ��return��䷵�أ�������Բο�java�Ļص�����
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setReadTimeout(8000);
					connection.setConnectTimeout(8000);
					connection.setRequestMethod("GET");
					InputStream inputStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder builder = new StringBuilder();
					String line = "";
					while((line = reader.readLine()) != null){
						builder.append(line);
					}
					
					if(listener != null){
						listener.onRead(builder.toString());
					}
				}  catch (Exception e) {
					if(listener != null){
						listener.onError(e);
					}
				}
				finally{ //����һ��Ҫ�ر�
					if(connection != null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
