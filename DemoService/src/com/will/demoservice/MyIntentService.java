package com.will.demoservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

// IntentService will use worker thread to finish the task one by one
public class MyIntentService extends IntentService {

	private static final String TAG = "MyIntentService";
	
	public MyIntentService() {
		super("MyIntentService");
		// TODO Auto-generated constructor stub
	}

	// If we start more than one service, onHandlerIntent() will finish the intent requisition one by one
	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		long endTime = System.currentTimeMillis() + 20 * 1000;
		Log.i(TAG, "onHandleIntent");
		
		while (System.currentTimeMillis() < endTime) {
			
			synchronized (this) {
				
				try {
					wait(endTime - System.currentTimeMillis());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Log.i(TAG, "-- Task has been finish! --");
	}

}
