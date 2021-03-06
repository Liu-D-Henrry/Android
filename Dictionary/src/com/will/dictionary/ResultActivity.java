package com.will.dictionary;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResultActivity extends Activity {

	ListView result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		Intent intent = this.getIntent();
		Bundle data = intent.getExtras();
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = (List<Map<String, String>>) data.getSerializable("data");
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.line, new String[] {"word", "detail"}, new int[] {R.id.resultWord, R.id.resultDetail});
		
		result = (ListView) findViewById(R.id.result);
		result.setAdapter(adapter);
	}

}
