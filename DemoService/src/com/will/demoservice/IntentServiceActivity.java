package com.will.demoservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IntentServiceActivity extends Activity {

	private static final String TAG = "IntentServiceActivity";
	
	Button startMyService, startIntentService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.intent_service_activity);
		
		startMyService = (Button) findViewById(R.id.startMyService);
		startIntentService = (Button) findViewById(R.id.startIntentService);
		
		startMyService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("com.will.demoservice.MY_SERVICE");
				startService(intent);
			}
			
		});
		
		startIntentService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("com.will.demoservice.MY_INTENT_SERVICE");
				startService(intent);
			}
			
		});
	}

}
