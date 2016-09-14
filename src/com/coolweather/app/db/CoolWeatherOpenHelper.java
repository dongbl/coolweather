/*******************************************
 *�ļ�����(����)CoolWeather/com.coolweather.app.db/CoolWeatherOpenHelper.java
 *
 *��     ��:dongbl
 *
 *��     ��:2016-9-13����11:46:02
********************************************/
package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/*********************************************
 *��  ��:CoolWeatherOpenHelper
 *��  ��:���ݿ����
 *
 *��  ��:dongbl
 *��  ��:2016-9-13
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
