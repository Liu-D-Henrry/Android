package com.will.phoneblocker;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

	private static final String TAG = "-- MyBaseExpandableListAdapter -- ";
	
	private final ArrayList<String> names;
	private BlackListActivity activity;
	private final ArrayList<ArrayList<String>> details;
	private HashMap<String, String> checkedItem = new HashMap<String, String>();
	private BlockerApplication application;
	
	public MyBaseExpandableListAdapter(BlackListActivity activity, final ArrayList<String> names, final ArrayList<ArrayList<String>>details) {
		super();
		this.activity = activity;
		this.names = names;
		this.details = details;
		
		application = (BlockerApplication) activity.getApplication();
	}
	
	public HashMap<String, String> getCheckedItem() {
		return this.checkedItem;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return details.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final String name = getGroup(groupPosition).toString();
		final String phone = getChild(groupPosition, childPosition).toString();
		CheckBox cb = new CheckBox(activity);
		cb.setPadding(100, 0, 0, 0);
		cb.setText(phone);
//		cb.setText("test");
		if (application.isBlack(phone)) {
			cb.setChecked(true);
		}
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton checkBox, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					checkedItem.put(phone, name);
				} else {
					checkedItem.remove(phone);
				}
				Log.d(TAG, checkedItem.toString());
			}
			
		});
		return cb;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return details.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return names.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return names.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String name = getGroup(groupPosition).toString();
		TextView tv = new TextView(activity);
		tv.setPadding(100, 0, 0, 0);
		tv.setText(name);
		return tv;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
