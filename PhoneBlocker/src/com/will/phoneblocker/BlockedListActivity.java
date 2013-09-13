package com.will.phoneblocker;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BlockedListActivity extends Activity {

	private static final String TAG = "-- BlockedListActivity --";
	private static final String SERVICE_INTENT = "com.will.phoneblocker.BLOCK_SERVICE";
//	private static final String TEST = "com.will.phoneblocker.TEST";
	
	private BlockerApplication application;
	private ActivityReceiver receiver;
	private ListView blockedList;
//	private Button test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blocked_list_activity);
		application = (BlockerApplication) getApplication();
		blockedList = (ListView) findViewById(R.id.blocked_list);
		/*
		test = (Button) findViewById(R.id.test);
		test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TEST);
				sendBroadcast(intent);
			}
			
		});
		*/
		
		receiver = new ActivityReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BlockerApplication.BROADCAST_ACTION);
		registerReceiver(receiver, filter);
		
		ArrayList<HashMap<String, String>> list = application.getBlockedList();
		SimpleAdapter adapter = new SimpleAdapter(BlockedListActivity.this, 
				list, 
				R.layout.blocked_item, 
				new String[] {"name", "ringTime", "phone"}, 
				new int[] {R.id.blocked_name, R.id.blocked_time, R.id.blocked_phone});
		blockedList.setAdapter(adapter);
	}
	
	public class ActivityReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onReceive");
			ArrayList<HashMap<String, String>> list = application.getBlockedList();
			Log.i(TAG, list.toString());
			SimpleAdapter adapter = new SimpleAdapter(BlockedListActivity.this, 
					list, 
					R.layout.blocked_item, 
					new String[] {"name", "ringTime", "phone"}, 
					new int[] {R.id.blocked_name, R.id.blocked_time, R.id.blocked_phone});
			blockedList.setAdapter(adapter);
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		
		switch (item.getItemId()) {
		case R.id.service:
			intent.setAction(SERVICE_INTENT);
			if (application.isServiceRunning()) {
				Toast.makeText(this, "Stop menu", Toast.LENGTH_LONG).show();
				item.setIcon(android.R.drawable.ic_media_play);
				boolean tmp = application.closeAutoStartService();
				Log.i(TAG, "auto_start_service=" + tmp);
				stopService(intent);
			} else {
				Toast.makeText(this, "Start menu", Toast.LENGTH_LONG).show();
				item.setIcon(android.R.drawable.ic_media_pause);
				boolean tmp = application.openAutoStartService();
				Log.i(TAG, "auto_start_service=" + tmp);
				startService(intent);
			}
			break;
		case R.id.blacklistactivity:
			Toast.makeText(this, "BlackList menu", Toast.LENGTH_LONG).show();
			intent.setClass(BlockedListActivity.this, BlackListActivity.class);
			startActivity(intent);
			break;
		}
		return true;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		MenuItem toggleItem = menu.findItem(R.id.service);
		if (application.isServiceRunning()) {
			toggleItem.setTitle(R.string.titleStop);
			toggleItem.setIcon(android.R.drawable.ic_media_pause);
		} else {
			toggleItem.setTitle(R.string.titleStart);
			toggleItem.setIcon(android.R.drawable.ic_media_play);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blocked_list_menu, menu);
		return true;
	}

}
