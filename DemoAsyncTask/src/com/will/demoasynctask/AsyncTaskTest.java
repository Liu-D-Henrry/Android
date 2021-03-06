package com.will.demoasynctask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AsyncTaskTest extends Activity {

	private static final String TAG = "AsyncTaskTest";
	
	TextView content;
	Button download;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_task_test);
        
        Log.i(TAG, "onCreate");
        
        content = (TextView) findViewById(R.id.content);
        download = (Button) findViewById(R.id.download);
        download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//Each AsyncTask instance just can be call one time, so we declare AysncTask instance here.
				DownloadTask task = new DownloadTask(AsyncTaskTest.this);
				try {
					// we must call execute() with AsyncTask instance in UI thread
					task.execute(new URL("http://www.crazyit.org/ethos.php"));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        });
    }
    
    /**
     * 
     * @author will
     * URL: URL is used as the param type of execute()
     * Integer: Integer is used as the param type of onProgressUpdate
     * String: String is used as the type of return value 
     */
    class DownloadTask extends AsyncTask<URL, Integer, String> {
    	
		Context myContext;
    	int hasRead;
    	ProgressDialog pdialog;
    	
    	//After doInBackground() has finished, this function will be called.
    	@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
    		content.setText(result);
    		pdialog.dismiss();
		}

    	//This function will be called before doInBackground()
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog = new ProgressDialog(myContext);
			pdialog.setTitle("Download Task");
			pdialog.setMessage("Downloading...");
			pdialog.setCancelable(false);
			pdialog.setMax(202);
			pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pdialog.setIndeterminate(false);
			pdialog.show();
		}

		//This function will be called when publishProgress() is called in doInBackground()
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			content.setText("Has read [ " + values + " ] lines");
			pdialog.setProgress(values[0]);
		}

    	public DownloadTask(Context context) {
    		myContext = context;
    	}

    	//Something we need to do and will consume a period of time are put here.
		@Override
		protected String doInBackground(URL... params) {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder();
			try {
				URLConnection conn = params[0].openConnection();
				Log.d(TAG, conn + "");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				Log.d(TAG, br + "");
				
				String line = null;
				while ((line = br.readLine()) != null) {
					Log.d(TAG, "read a line");
					sb.append(line);
					hasRead++;
					publishProgress(hasRead);
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

    }
}
