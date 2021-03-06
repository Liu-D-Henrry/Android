package com.will.demofragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThirdFragment extends Fragment {

	String message = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (getArguments() != null)
		{
			if (getArguments().containsKey("create")) {
				message = getArguments().getString("create");
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.third_fragment, container, false);
		
		if (message != null) {
			TextView msg = (TextView) rootView.findViewById(R.id.msg);
			msg.setText(message);
		}
		
		return rootView;
	}

}
