package com.will.demoservice; 
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private static final String TAG = "MyService";
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		long endTime = System.currentTimeMillis() + 20 * 1000;
		Log.i(TAG, "onStartCommand");
		
		while (System.currentTimeMillis() < endTime) {
			
			synchronized (this) {

				try {
					Log.i(TAG, "waiting!!");
					wait(endTime - System.currentTimeMillis());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		
		Log.i(TAG, "-- Task has been finished! --");
		return START_STICKY;
	}

}
