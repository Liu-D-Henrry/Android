package com.will.demochangeorientation;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button bn;
	Configuration config;
	Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		config = getResources().getConfiguration();
		activity = this;
		
		bn = (Button) findViewById(R.id.bn);
		bn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setOrientation(activity, config);
			}
		});
		
		setOrientation(activity, config);
	}
	
	private void setOrientation(Activity activity, Configuration config) {
		
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			bn.setText("Click to change to Portrait");
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			bn.setText("Click to change to Landspace");
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
