package com.will.phoneblocker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "-- MySQLiteOpenHelper --";
	private static final String CREATE_BLACK_LIST_TABLE_SQL = "create table blacklist(_id integer primary key autoincrement, name, phone unique)";
	private static final String CTEATE_BLOCKED_LIST_TABLE_SQL = "create table blockedlist(_id integer primary key autoincrement, name, phone, ringTime datetime)";
	private String name;
	
	public MySQLiteOpenHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		if (name.equals(BlockerApplication.DATABASE_BLACK_LIST)) {
			db.execSQL(CREATE_BLACK_LIST_TABLE_SQL);
		} else if (name.equals(BlockerApplication.DATABASE_BLOCKED_LIST)) {
			db.execSQL(CTEATE_BLOCKED_LIST_TABLE_SQL);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnUpgrade -- oldVersion: " + oldVersion + "; newVersion: " + newVersion);
	}
	
}
