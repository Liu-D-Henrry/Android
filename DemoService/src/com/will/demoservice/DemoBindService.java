package com.will.demoservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

// onCreate --> onBind --> running --> onUnbind --> onDestroy
// After the service is started, it can not started again.

// If a service is started by startService(), then call bindService
// After that, if override onUnbind() to return true, it will recall onRebind() after call onUnbind() then bind again.
public class DemoBindService extends Service {

	private static final String TAG = "DemoBindService";
	
	private int count;
	private boolean quit;
	private Binder binder = new MyBinder();
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreate");
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!quit) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
				}
			}
			
		}.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		quit = true;
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
		Log.i(TAG, "onRebind");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		super.onUnbind(intent);
		Log.i(TAG, "onUnbind");
		//return true to call onRebind if we call bind next time.
		// This service won't be destroy after call onUnbind().
		return true;
	}

	public class MyBinder extends Binder {
		
		public int getCount() {
			return count;
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onBind");
		return binder;
	}

}
