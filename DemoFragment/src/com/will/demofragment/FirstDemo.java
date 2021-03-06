package com.will.demofragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FirstDemo extends Activity implements FirstFragment.Callbacks {

	TextView show;
	Button button1;
	Button button2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_demo);
		
		show = (TextView) findViewById(R.id.show);
		button1 = (Button) findViewById(R.id.fdButton1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment fragment = getFragmentManager().findFragmentById(R.id.second_fragment);
				TextView tv = (TextView) fragment.getView().findViewById(R.id.msg);
				tv.setText(show.getText().toString());
			}
			
		});
		
		button2 = (Button) findViewById(R.id.fdButton2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle arguments = new Bundle();
				arguments.putString("create", "This Fragment is created by Activity!");
				ThirdFragment fragment = new ThirdFragment();
				fragment.setArguments(arguments);
				
				// The contents would remain visible if we instantiated Fragment within xml layout.
				// To solve this problem, we use FrameLayout instead.
				getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
			}
			
		});
	}

	@Override
	public void clickButton(Integer id) {
		// TODO Auto-generated method stub
		switch (id)
		{
		case 1:
			show.setText("Button 1 in First Fragment was clicked!");
			break;
		case 2:
			show.setText("Button 2 in First Fragment was clicked!");
			break;
		}
	}
	
}
