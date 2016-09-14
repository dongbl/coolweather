/*******************************************
 *文件名：(工程)CoolWeather/com.coolweather.app.db/CoolWeatherOpenHelper.java
 *
 *作     者:dongbl
 *
 *日     期:2016-9-13下午11:46:02
********************************************/
package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/*********************************************
 *类  名:CoolWeatherOpenHelper
 *功  能:数据库相关
 *
 *作  者:dongbl
 *日  期:2016-9-13
 *******************************************/
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	public CoolWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Table.TABLE_PROVINCE);
		db.execSQL(Table.TABLE_CITY);
		db.execSQL(Table.TABLE_COUNTY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

   
}
