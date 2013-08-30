package com.will.demofragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FirstFragment extends Fragment {

	Button button1, button2;
	Activity activity;
	private Callbacks myCallbacks;
	
	// Declare a callback interface. It need to be implemented in Activity.
	// This Fragment will interact with Activity through this interface.
	public interface Callbacks {
		
		public void clickButton(Integer id);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("The Activity that contain First Fragment does not implement Callbacks interface");
		}
		myCallbacks = (Callbacks) activity;
	}

	// In Fragment, we need to initialize component in onCreateView().
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.first_fragment, container, false);
		button1 = (Button) rootView.findViewById(R.id.ffButton1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Here, we call Activity's clickButton() to interact with Activity.
				myCallbacks.clickButton(1);
			}
			
		});
		button2 = (Button) rootView.findViewById(R.id.ffButton2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Here, we call Activity's clickButton() to interact with Activity.
				myCallbacks.clickButton(2);
			}
			
		});
		return rootView;
	}

}
