package com.example.helloworld;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.view.View;
import android.widget.Button;
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

	public JSONObject friends;

	private void initFriends() {

		try {
			InputStream fString = this.getResources().openRawResource(
					R.raw.friends);
			byte[] buffer = new byte[fString.available()];
			while (fString.read(buffer) != -1)
				;
			String json = new String(buffer);
			friends = new JSONObject(json);

		} catch (Exception je) {
			je.printStackTrace();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initFriends();

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
			JSONArray items = friends.getJSONArray("items");
			JSONObject item = items.optJSONObject(0);
			mainButton.setText(item.getString("name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
}
