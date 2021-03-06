package com.will.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	final String CREATE_TABLE_SQL = "create table dict(_id integer primary key autoincrement, word, detail)";
	
	public MyDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_SQL);
		if (db != null) {
			Log.d("MyDatabaseHelper", db + "");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("----- onUpdate Called -----" + oldVersion + "---->" + newVersion);
	}

}
