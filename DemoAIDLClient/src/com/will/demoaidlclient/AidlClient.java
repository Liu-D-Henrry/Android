package com.will.demoaidlclient;

// Please pay attention here: the path of ICat in Client must be the same as in Service
import com.will.demoaidlservice.ICat;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
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
import android.widget.TextView;

public class AidlClient extends Activity {

	private static final String TAG = "AidlClient";
	private ICat catService;
	
	TextView color, weight;
	Button get;
	
	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder service) {
			// TODO Auto-generated method stub
			Log.i(TAG, "-- Service Connect! --");
			catService = ICat.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG, "-- Service Disconnect! --");
			
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aidl_client_activity);
		
		Intent intent = new Intent();
		intent.setAction("com.will.demoaidlservice.action.AIDL_SERVICE");
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
		
		color = (TextView) findViewById(R.id.color);
		weight = (TextView) findViewById(R.id.weight);
		get = (Button) findViewById(R.id.get);
		get.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					
					color.setText("Color: " + catService.getColor().toString());
					weight.setText("Weight: " + catService.getWeight());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(conn);
	}

}
