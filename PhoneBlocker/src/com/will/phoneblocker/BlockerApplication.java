package com.will.phoneblocker;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BlockerApplication extends Application {

	private static final String TAG = "BlockerApplication";
	
	public static final String DATABASE_BLACK_LIST = "BlackList.db3";
	public static final String TABLE_BLACK_LIST = "blacklist";
	public static final String DATABASE_BLOCKED_LIST = "BlockedList.db3";
	public static final String TABLE_BLOCKED_LIST = "blockedlist";
	public static final int INSERT_SUCCESSFULLY = 1;
	public static final int PHONE_EXISIT = -1;
	public static final int TABLE_DOES_NOT_EXIST = 0;
	
	public static final String BROADCAST_ACTION = "com.will.phoneblocker.UPDATE";
	
	public static final String AUTO_SERVICE = "auto_service";
	public static final String AUTO_SERVICE_ITEM = "auto_start_service";
	
	private boolean serviceRunning;
	private MySQLiteOpenHelper BlackListDbHelper;
	private MySQLiteOpenHelper BlockedListDbHelper;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		BlackListDbHelper = new MySQLiteOpenHelper(this, DATABASE_BLACK_LIST, 1);
		BlockedListDbHelper = new MySQLiteOpenHelper(this, DATABASE_BLOCKED_LIST, 1);
		preferences = this.getSharedPreferences(AUTO_SERVICE, MODE_PRIVATE);
		editor = preferences.edit();
	}
	
	public boolean openAutoStartService() {
		editor.putBoolean(AUTO_SERVICE_ITEM, true);
		editor.commit();
		return preferences.getBoolean(AUTO_SERVICE_ITEM, false);
	}

	public boolean closeAutoStartService() {
		editor.putBoolean(AUTO_SERVICE_ITEM, false);
		editor.commit();
		return preferences.getBoolean(AUTO_SERVICE_ITEM, true);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (BlackListDbHelper != null) {
			BlackListDbHelper.close();
		}
		super.onTerminate();
	}

	public boolean isBlack(String phone) {
		ArrayList<HashMap<String, String>> blackNumberList = getBlackList();
		for (int i = 0; i < blackNumberList.size(); i++) {
			HashMap<String, String> map = blackNumberList.get(i);
			for (Object key : map.keySet()) {
				if (phone.equals(map.get(key))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<HashMap<String, String>> getBlockedList() {
		ArrayList<HashMap<String, String>> blockedNumberList = new ArrayList<HashMap<String, String>>();
		SQLiteDatabase db = BlockedListDbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_BLOCKED_LIST, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
			map.put("ringTime", cursor.getString(cursor.getColumnIndex("ringTime")));
			blockedNumberList.add(map);
			Log.d(TAG, blockedNumberList + "");
		}
		
		return blockedNumberList;
	}
	
	public ArrayList<HashMap<String, String>> getBlackList() {
		ArrayList<HashMap<String, String>> blackNumberList = new ArrayList<HashMap<String, String>>();
		SQLiteDatabase db = BlackListDbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_BLACK_LIST, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
			blackNumberList.add(map);
		}
		
		return blackNumberList;
	}
	
	public String getName(String phone) {
		ArrayList<HashMap<String, String>> blackNumberList = getBlackList();
		for (int i = 0; i < blackNumberList.size(); i++) {
			HashMap<String, String> map = blackNumberList.get(i);
			if (phone.equals(map.get("phone"))) {
				return map.get("name");
			}
		}
		return "Unknown";
	}
	
	public int insertData(String tableName, String name, String phone, String ringTime) {
		Log.d(TAG, tableName);
		if (tableName.equals(TABLE_BLACK_LIST)) {
			SQLiteDatabase BlackListDb = BlackListDbHelper.getReadableDatabase();
			Log.d(TAG, name);
			Log.d(TAG, phone);
			Cursor cursor = BlackListDb.rawQuery("select * from " + TABLE_BLACK_LIST + " where phone like ?", new String[] {phone});
			Log.d(TAG, "cursor count = " + cursor.getCount());
			if (cursor.getCount() == 0) {
				BlackListDb.execSQL("insert into " + TABLE_BLACK_LIST + " values(null, ?, ?)", new String[] {name, phone});
				return INSERT_SUCCESSFULLY;
			} else {
				return PHONE_EXISIT;
			}
		} else if (tableName.equals(TABLE_BLOCKED_LIST)) {
			SQLiteDatabase BlockedListDb = BlockedListDbHelper.getReadableDatabase();
			Log.d(TAG, name);
			Log.d(TAG, phone);
			Log.d(ringTime, ringTime);
			BlockedListDb.execSQL("insert into " + TABLE_BLOCKED_LIST + " values(null, ?, ?, ?)", new String[] {name, phone, ringTime});
			return INSERT_SUCCESSFULLY;
		}
		
		return TABLE_DOES_NOT_EXIST;
	}
	
	public void deleteData(String tableName, String name, String phone, String ringTime) {
		Log.d(TAG, "tableNaem=" + tableName);
		if (tableName.equals(TABLE_BLACK_LIST)) {
			SQLiteDatabase BlackListDb = BlackListDbHelper.getReadableDatabase();
			Log.d(TAG, name);
			Log.d(TAG, phone);
			BlackListDb.execSQL("delete from " + TABLE_BLACK_LIST + " where name = ? and phone = ?", new String[] {name, phone});
		}
	}
	
	public boolean isServiceRunning() {
		return this.serviceRunning;
	}
	
	public void setServiceRunning(boolean isServiceRunning) {
		this.serviceRunning = isServiceRunning;
	}
}
