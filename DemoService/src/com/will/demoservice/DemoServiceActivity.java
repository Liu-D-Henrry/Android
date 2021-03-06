package com.will.demoservice;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DemoServiceActivity extends Activity {
	
	private static final String TAG = "DemoServiceActivity";
	
	Button startService, stopService, startBindService, stopBindService, bindService, unbindService, startIntentService;
	Button getServiceStatus;
	
	DemoBindService.MyBinder binder;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder service) {
			// TODO Auto-generated method stub
			Log.i(TAG, "-- Service Connected --");
			binder = (DemoBindService.MyBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG, "-- Service Disconnected --"); }
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_activity);
		startService = (Button) findViewById(R.id.startService);
		stopService = (Button) findViewById(R.id.stopService);
		startBindService = (Button) findViewById(R.id.startBindService);
		stopBindService = (Button) findViewById(R.id.stopBindService);
		bindService = (Button) findViewById(R.id.bindService);
		unbindService = (Button) findViewById(R.id.unbindService);
		startIntentService = (Button) findViewById(R.id.startIntentService);
		getServiceStatus = (Button) findViewById(R.id.serviceStatus);
		
		ButtonClickListener listener = new ButtonClickListener();
		
		startService.setOnClickListener(listener);
		stopService.setOnClickListener(listener);
		
		startBindService.setOnClickListener(listener);
		stopBindService.setOnClickListener(listener);
		bindService.setOnClickListener(listener);
		unbindService.setOnClickListener(listener);
		getServiceStatus.setOnClickListener(listener);
		
		startIntentService.setOnClickListener(listener);
	}
	
	private class ButtonClickListener implements OnClickListener {

		Intent intent;
		
		public ButtonClickListener() {
			intent = new Intent();
		}
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			
			case R.id.startService:
				Log.i(TAG, "Trying to start service");
				intent.setAction("com.will.demoservice.START_SERVICE");
			case R.id.startBindService:
				if (view.getId() == R.id.startBindService) {
					Log.i(TAG, "Trying to start bind service");
					intent.setAction("com.will.demoservice.BIND_SERVICE");
				}
				startService(intent);
				break;
				
			case R.id.stopService:
				Log.i(TAG, "Trying to stop service");
				intent.setAction("com.will.demoservice.START_SERVICE");
			case R.id.stopBindService:
				if (view.getId() == R.id.stopBindService) {
					Log.i(TAG, "Trying to stop bind service");
					intent.setAction("com.will.demoservice.BIND_SERVICE");
				}
				stopService(intent);
				break;
				
			case R.id.bindService:
				Log.i(TAG, "Trying to bind service");
				intent.setAction("com.will.demoservice.BIND_SERVICE");
				bindService(intent, conn, Service.BIND_AUTO_CREATE);
				break;
				
			case R.id.unbindService:
				Log.i(TAG, "Trying to unbind service");
				unbindService(conn);
				break;
				
			case R.id.serviceStatus:
				Toast.makeText(DemoServiceActivity.this, "Count in Service is " + binder.getCount(), Toast.LENGTH_LONG).show();
				break;
				
			case R.id.startIntentService:
				Log.i(TAG, "Trying to start IntentServiceActivity");
				intent.setComponent(new ComponentName(DemoServiceActivity.this, IntentServiceActivity.class));
				startActivity(intent);
				break;
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
