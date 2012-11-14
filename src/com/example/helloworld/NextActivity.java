package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class NextActivity extends Activity {

	public RadioButton radio0,radio1,radio2;
	public TextView answer;
	public RadioGroup options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		
		options = (RadioGroup) findViewById(R.id.opt);
		options.setOnCheckedChangeListener(onChangeRadio);
		
		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);
		answer = (TextView) findViewById(R.id.answer);
		
	}
	
	private RadioGroup.OnCheckedChangeListener onChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		
	    public void onCheckedChanged(RadioGroup group, int checkedId)
	    {
	    	if(checkedId == radio0.getId()) {
	    		
	    		answer.setText(radio0.getText());
	    	} else if (checkedId == radio1.getId()) {
	    		
	    		answer.setText(radio1.getText());
	    	} else if (checkedId == radio2.getId()) {
	    		
	    		answer.setText(radio2.getText());
	    	}
	    	
	    	
	    	
     
	    }
	};

}
