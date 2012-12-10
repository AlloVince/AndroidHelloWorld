package com.example.helloworld;


import android.os.Bundle;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;

import android.content.Intent;


import org.json.*;

public class YesnoActivity extends Activity {

	public JSONObject questions;
	
	public Button yesBtn;
	
	public Button noBtn;
	
	public TextView questionTextView;
	
	public int questionIndex = 1;
	
	public int choice;

	private void initData() {
		try {
			InputStream fString = this.getResources().openRawResource(R.raw.yesno);
			byte[] buffer = new byte[fString.available()];
			while (fString.read(buffer) != -1);
			String json = new String(buffer);
			questions = new JSONObject(json);

		} catch (Exception je) {
			je.printStackTrace();
		}

	}
	
	private void initView()
	{
		
		yesBtn = (Button) findViewById(R.id.btnYes);
		noBtn = (Button) findViewById(R.id.btnNo);
		
		questionTextView = (TextView) findViewById(R.id.textQuestion);
	
	}
	
	private void setDataToView() {
		try {
			
			if(questionIndex == 999){
				
				yesBtn.setOnClickListener(null);
				noBtn.setOnClickListener(null);
				
				String hint = choice == 1 ? "嘿，别着急嘛，听听他们怎么说" : "就知道你会选这个，那来听听他们怎么说";
				
				final Toast toast = Toast.makeText(getApplicationContext(), hint , Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
		        Timer timer = new Timer();
		        timer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		                // TODO Auto-generated method stub
		                Intent goIntent = new Intent();
		                goIntent.setClass(YesnoActivity.this, NextActivity.class);
		                toast.show();
		                startActivity(goIntent);
		            }
		        }, 1*1000);
				
				
			} else {
				
				JSONArray items = questions.getJSONArray("items");
				JSONObject item = items.optJSONObject(questionIndex);
				
				String currentQuestion = (String) item.get("question").toString();
				questionTextView.setText(currentQuestion);
							
			}


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yesno);

		initData();
		initView();
		
		setDataToView();
		
		yesBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					choice = 1;
					onYesBtnClick("Yes");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		noBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					choice = 0;
					onYesBtnClick("No");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
		
	protected void onYesBtnClick(String btnText) throws JSONException {

		JSONArray items = questions.getJSONArray("items");
		try {
			JSONObject item = items.optJSONObject(questionIndex);
			int nextIndex = item.getJSONObject("res").getInt(btnText);
			questionIndex = nextIndex;
		} catch (Exception e){
			questionIndex = 999;
		} 
		
		setDataToView();
	}

}
