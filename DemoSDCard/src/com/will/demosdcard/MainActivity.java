package com.will.demosdcard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private static final String FILE_NAME = "/DemoSDCrad.txt";
	
	EditText content;
	TextView show;
	Button write, read, openExplorer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_sdcard);
		
		content = (EditText) findViewById(R.id.writeContent);
		show = (TextView) findViewById(R.id.show);
		write = (Button) findViewById(R.id.write);
		read = (Button) findViewById(R.id.read);
		openExplorer = (Button) findViewById(R.id.filelist);
		
		write.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				write(content.getText().toString());
				content.setText("");
			}
			
		});
		
		read.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show.setText(read());
			}
			
		});
		
		openExplorer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, FileExplorer.class));
			}
			
		});
	}
	
	private String read() {
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				
				File sdCardDir = Environment.getExternalStorageDirectory();
				FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + FILE_NAME);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				StringBuilder sb = new StringBuilder("show:\n");
				String line = null;
				
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				
				br.close();
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void write(String content) {
		
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				Log.i(TAG, sdCardDir.getCanonicalPath().toString());
				File targetFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);
				
				RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
				raf.seek(targetFile.length());
				raf.write(content.getBytes());
				raf.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
