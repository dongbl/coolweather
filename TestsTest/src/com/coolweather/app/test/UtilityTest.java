package com.coolweather.app.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coolweather.app.util.Utility;

import android.test.AndroidTestCase;
import android.util.Log;

public class UtilityTest extends AndroidTestCase {
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testUtility(){
		String string = "01|北京,02|上海,03|广州";
		
		Map<String, String> map = new HashMap<String, String>();
		map = Utility.getData(string);
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String id = iterator.next();
			Log.d("dongbl", id);
			Log.d("dongbl", map.get(id));
		}
		
	}

}
