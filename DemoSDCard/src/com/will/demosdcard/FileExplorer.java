package com.will.demosdcard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FileExplorer extends Activity {

	private static final String TAG = "FileExplorer";
	
	private static String ROOT;
	
	TextView path;
	ListView list;
	Button parent;
	
	File currentParent;
	File[] currentFiles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_list);
		
		path = (TextView) findViewById(R.id.path);
		list = (ListView) findViewById(R.id.list);
		parent = (Button) findViewById(R.id.parent);
		
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			
			try {
				File root = new File(Environment.getExternalStorageDirectory().getCanonicalPath());
				ROOT = root.getCanonicalPath().toString();
				currentParent = root;
				currentFiles = root.listFiles();
				inflateListView(currentFiles);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if (!currentParent.getCanonicalPath().equals(ROOT)) {
						currentParent = currentParent.getParentFile();
						currentFiles = currentParent.listFiles();
						inflateListView(currentFiles);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				if (currentFiles[position].isFile()) return;
				
				File[] tmp = currentFiles[position].listFiles();
				if (tmp == null || tmp.length == 0) {
					Toast.makeText(FileExplorer.this, "Current path can not be accessed or no file in this path", Toast.LENGTH_LONG).show();
				} else {
					currentParent = currentFiles[position];
					currentFiles = currentParent.listFiles();
					inflateListView(currentFiles);
				}
			}
			
		});
	}
	
	private void inflateListView(File[] files) {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			if (files[i].isDirectory()) {
				listItem.put("icon", R.drawable.folder);
			} else {
				listItem.put("icon", R.drawable.file);
			}
			listItem.put("fileName", files[i].getName());
			listItems.add(listItem);
		}
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.line, new String[] {"icon", "fileName"}, new int[] {R.id.icon, R.id.file_name});
		list.setAdapter(simpleAdapter);
		try {
			path.setText("Current path: " + currentParent.getCanonicalPath().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
