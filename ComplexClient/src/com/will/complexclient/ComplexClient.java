package com.will.complexclient;

import java.util.List;

import com.will.complexservice.IPet;
import com.will.complexservice.Person;
import com.will.complexservice.Pet;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ComplexClient extends Activity {
	
	private static final String TAG = "ComplexService";
	
	private IPet petService;
	private Button get;
	private EditText owner;
	private ListView show;

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			petService = IPet.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			petService = null;
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complex_client);
		
		Log.i(TAG, "onCreate");
		
		get = (Button) findViewById(R.id.get);
		owner = (EditText) findViewById(R.id.owner);
		show = (ListView) findViewById(R.id.pets);
		
		Intent intent = new Intent();
		intent.setAction("com.will.complexservice.COMPLEX_SERVICE");
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
		
		get.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					String name = owner.getText().toString();
					List<Pet> pets = petService.getPets(new Person(1, name, name));
					
					ArrayAdapter<Pet> adapter = new ArrayAdapter<Pet>(ComplexClient.this, android.R.layout.simple_list_item_1, pets);
					show.setAdapter(adapter);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(conn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.complex_client, menu);
		return true;
	}

}
