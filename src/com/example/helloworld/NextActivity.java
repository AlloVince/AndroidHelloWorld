package com.example.helloworld;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class NextActivity extends Activity {

	public JSONObject people;
	public Button nextBtn;
	public Button prevBtn;
	public Button replyButton;
	public ImageView faceImageView;
	public MediaPlayer voice;
	public String currentVoice;
	
	public int currentPerson = 0;
	public int maxPerson = 10;
	
	private void initData() {
		try {
			InputStream fString = this.getResources().openRawResource(R.raw.people);
			byte[] buffer = new byte[fString.available()];
			while (fString.read(buffer) != -1);
			String json = new String(buffer);
			people = new JSONObject(json);
			//maxPerson = people.getJSONArray("items").length();
			
			voice = new MediaPlayer();

		} catch (Exception je) {
			je.printStackTrace();
		}

	}
	
	private void initView()
	{
		
		nextBtn = (Button) findViewById(R.id.nextBtn);
		prevBtn = (Button) findViewById(R.id.prevBtn);
		replyButton = (Button) findViewById(R.id.replayBtn);
		
		faceImageView = (ImageView) findViewById(R.id.faceImage);
	
	}
	
	protected void changeFace(String faceName)
	{
	    AssetManager am = null;  
	    am = getAssets();  
	    try {
	    	
			InputStream assetFile = am.open(faceName);
			Bitmap bm = BitmapFactory.decodeStream(assetFile);
			
			faceImageView.setImageBitmap(bm);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	protected void playVoice(String voiceName){

		currentVoice = voiceName;
	    AssetManager am = null;  
	    am = getAssets(); 
		AssetFileDescriptor fileDescriptor;

		
		try {
			fileDescriptor = am.openFd(voiceName);
			voice.reset();
			voice.setDataSource(fileDescriptor.getFileDescriptor(),
			        fileDescriptor.getStartOffset(),  
			        fileDescriptor.getLength());
			fileDescriptor.close();
			if(voice.isPlaying()) {
				voice.stop();
			}
			

			voice.prepare();
			voice.start();
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		
		initData();
		initView();
		
		try {
			changePerson(0);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		replyButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					replay();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		nextBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					next();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		prevBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					prev();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    
		//Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" +"pro/huxin/face.jpg");
	}
	
	protected void replay() throws JSONException, IllegalStateException, IOException {	
		if(currentVoice != null){
			playVoice(currentVoice);
		}
	}
	
	protected void changePerson(int index) throws JSONException {
		JSONArray items = people.getJSONArray("items");
		JSONObject item = items.optJSONObject(index);
		currentPerson = index;
		String hisVoice = "Pro/" + item.getString("name") + "/" + item.getString("voice");
		String hisFace =  "Pro/" + item.getString("name") + "/" + item.getString("face");
		
		changeFace(hisFace);
		playVoice(hisVoice);
	}
	
	protected void next() throws JSONException {
		currentPerson++;
		if(currentPerson >= maxPerson){
			
			
		} else {
			
			changePerson(currentPerson);
		}
		
	}
	
	protected void prev() throws JSONException {
		currentPerson = currentPerson > 0 ? currentPerson - 1 : 0;
		changePerson(currentPerson);
	}
}
