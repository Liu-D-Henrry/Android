package com.will.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dict extends Activity {

	private static final String TAG = "Dict";
	MyDatabaseHelper dbHelper;
	
	EditText word, detail;
	Button insert, search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		dbHelper = new MyDatabaseHelper(this, "myDict.db3", 1);
		
		word = (EditText) findViewById(R.id.word);
		detail = (EditText) findViewById(R.id.detail);
		
		insert = (Button) findViewById(R.id.insert);
		insert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				String textWord = word.getText().toString();
				Log.i(TAG, textWord);
				String textDetail = detail.getText().toString();
				Log.i(TAG, textDetail);
				Log.d(TAG, "word: " + textWord +"; detail: " + textDetail);
				insertData(db, textWord, textDetail);
				word.setText("");
				detail.setText("");
				db.close();
				Toast.makeText(Dict.this, "Add new Word successfully!!", Toast.LENGTH_LONG).show();
			}
			
		});
		
		search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i(TAG, "on click search button");
				String key = ((EditText) findViewById(R.id.key)).getText().toString();
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor cursor = db.rawQuery("select * from dict where word like ? or detail like ?", new String[] {"%" + key + "%", "%" + key + "%"});
				Bundle data = new Bundle();
				data.putSerializable("data", converCursorToList(cursor));
				Intent intent = new Intent(Dict.this, ResultActivity.class);
				intent.putExtras(data);
				startActivity(intent);
			}
			
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	private ArrayList<Map<String, String>> converCursorToList(Cursor cursor) {
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			Log.i(TAG, cursor.getString(1));
			map.put("word", cursor.getString(1));
			Log.i(TAG, cursor.getString(2));
			map.put("detail", cursor.getString(2));
			result.add(map);
		}
		
		return result;
	}
	
	private void insertData(SQLiteDatabase db, String word, String detail) {
		db.execSQL("insert into dict values(null, ?, ?)", new String[] {word, detail});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dict, menu);
		return true;
	}

}
