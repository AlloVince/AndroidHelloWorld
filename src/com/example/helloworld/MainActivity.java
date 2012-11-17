package com.example.helloworld;


import android.os.Bundle;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.view.Menu;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.io.InputStream;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;

import android.content.Intent;


import org.json.*;

public class MainActivity extends Activity {

	public JSONObject questions;
	
	public Button nextBtnButton;
	
	public TextView questionTextView;
	
	public RadioGroup radioGroup;
	
	public RadioButton[] opt;
	
	public int questionIndex = 0;

	private void initData() {
		try {
			InputStream fString = this.getResources().openRawResource(R.raw.start);
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
		
		nextBtnButton = (Button) findViewById(R.id.next_btn);
		
		questionTextView = (TextView) findViewById(R.id.question_view);
		
		radioGroup = (RadioGroup) findViewById(R.id.options);
		
		opt = new RadioButton[4];
		opt[0] = (RadioButton) findViewById(R.id.opt_1);
		
		opt[1] = (RadioButton) findViewById(R.id.opt_2);
		
		opt[2] = (RadioButton) findViewById(R.id.opt_3);
		
		opt[3] = (RadioButton) findViewById(R.id.opt_4);		
	}
	
	private void setDataToView() {
		try {
			JSONArray items = questions.getJSONArray("items");
			JSONObject item = items.optJSONObject(questionIndex);
			
			String currentQuestion = (String) item.get("question").toString();
			questionTextView.setText(currentQuestion);
			
			JSONArray optsArray = item.getJSONArray("options");
			
			int optsLength = optsArray.length();
			int i = 0;
			
			for(i = 0; i < optsLength; i++){

				opt[i].setText(optsArray.getString(i));
				opt[i].setVisibility(0);	

			}
			
			for(i = i; i < opt.length; i++){
				
				opt[i].setVisibility(4);
			}
			
			
			

			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		initView();
		
		setDataToView();

		nextBtnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onNextBtnClick();
			}

		});
		


		/*
		Button mainButton = (Button) findViewById(R.id.button1);
		mainButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				onMainBtnClick();
			}
		});

		Button nextButton = (Button) findViewById(R.id.button2);
		nextButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				onNextBtnClick();
			}
		});

		try {
			JSONArray items = questions.getJSONArray("items");
			JSONObject item = items.optJSONObject(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

	}
	
	protected void onNextBtnClick() {
		questionIndex++;
		setDataToView();
	}

	/*
	protected void onMainBtnClick() {
		MediaPlayer _mp;
		_mp = MediaPlayer.create(this, R.raw.voice1);
		if (_mp != null) {
			_mp.stop();

		}

		try {
			_mp.prepare();
			_mp.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void onNextBtnClick() {
		Intent goIntent = new Intent();
		goIntent.setClass(MainActivity.this, NextActivity.class);
		try {
			startActivity(goIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	*/
}
