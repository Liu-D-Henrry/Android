package com.will.dictionarytool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.will.dictionary.Words;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DictionaryTool extends Activity {
	
	ContentResolver contentResolver;

	Button insert, search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		contentResolver = this.getContentResolver();
		insert = (Button) findViewById(R.id.insert);
		insert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String word = ((EditText) findViewById(R.id.word)).getText().toString();
				String detail = ((EditText) findViewById(R.id.detail)).getText().toString();
				ContentValues values = new ContentValues();
				values.put(Words.Word.WORD, word);
				values.put(Words.Word.DETAIL, detail);
				contentResolver.insert(Words.Word.DICT_CONTENT_URI, values);
				Toast.makeText(DictionaryTool.this, 
						"Add the new word successfully!", 
						Toast.LENGTH_LONG)
						.show();
			}
			
		});
		
		search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String key = ((EditText) findViewById(R.id.key)).getText().toString();
				Cursor cursor = contentResolver.query(Words.Word.DICT_CONTENT_URI, 
						null, 
						"word like ? or detail like ?", 
						new String[] {"%" + key + "%", "%" + key + "%"}, 
						null);
				Bundle data = new Bundle();
				data.putSerializable("data", converCursorToList(cursor));
				Intent intent = new Intent(DictionaryTool.this, ResultActivity.class);
				intent.putExtras(data);
				startActivity(intent);
			}

			private Serializable converCursorToList(Cursor cursor) {
				// TODO Auto-generated method stub
				ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
				
				while (cursor.moveToNext()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put(Words.Word.WORD, cursor.getString(1));
					map.put(Words.Word.DETAIL, cursor.getString(2));
					result.add(map);
				}
				return result;
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dictionary_tool, menu);
		return true;
	}

}
