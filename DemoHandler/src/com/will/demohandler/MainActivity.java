package com.will.demohandler;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private static final String UPPER_NUM = "upper"; 
	EditText etNum;
	Button bnCal;
	TextView tvResult;
	CalThread calThread;
	Handler myHandler = null;
	
	class CalThread extends Thread {
		
		public Handler threadHandler;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 1.Construct Looper instance
			Looper.prepare();
			// 2.Construct Handler instance and override handleMessage function
			threadHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if (msg.what == 0x123) {
						int upper = msg.getData().getInt(UPPER_NUM);
						List<Integer> nums = new ArrayList<Integer>();
						outer:
							for (int i = 2; i <= upper; i++) {
								for (int j = 2; j <= Math.sqrt(i); j++) {
									if (i != 2 && i % j == 0) {
										continue outer;
									}
								}
								nums.add(i);
							}
						Message threadMsg = new Message();
						threadMsg.what = 0x123;
						Bundle threadBundle = new Bundle();
						threadBundle.putString(UPPER_NUM, nums.toString());
						threadMsg.setData(threadBundle);
						myHandler.sendMessage(threadMsg);
					}
				}
				
			};
			// 3.Start Looper instance
			Looper.loop();
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        
        Log.i(TAG, "onCreate");
        
        etNum = (EditText) findViewById(R.id.editNum);
        bnCal = (Button) findViewById(R.id.calculate);
        tvResult = (TextView) findViewById(R.id.result);
        bnCal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 0x123;
				Bundle bundle = new Bundle();
				bundle.putInt(UPPER_NUM, Integer.parseInt(etNum.getText().toString()));
				msg.setData(bundle);
				calThread.threadHandler.sendMessage(msg);
			}
        	
        });
        
        // In UI thread, system has help us construct a Looper instance, 
        // so we just need to construct an Handler isntance.
        myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					String nums = msg.getData().getString(UPPER_NUM);
					tvResult.setText(nums);
				}
			}
        	
        };
        
        calThread = new CalThread();
        calThread.start();
    }
}
