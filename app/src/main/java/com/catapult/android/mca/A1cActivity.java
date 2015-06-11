package com.catapult.android.mca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class A1cActivity extends Activity {
	
	String authToken = null;
	String dob, patId, patName = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a1c);
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
		
		EditText text = (EditText) findViewById(R.id.a1c_patientIDEditBox);
		text.setText(patId);
		
		EditText text2 = (EditText) findViewById(R.id.a1c_DOBEditBox);
		text2.setText(dob);
		
	}
	
	public void a1cBeginAnalysis(View v) {
		System.out.println("Inside A1CSetupActivity");
		Intent homeIntent = new Intent(getApplicationContext(),
				ManualClinicalMetricsActivity.class);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
		
	}
	
	 public void close(View view) {
			System.out.println("Inside A1CActivity:close");
			Intent homeIntent = new Intent(getApplicationContext(),
					ClinicScheduleActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			homeIntent.putExtra("authToken", authToken);
			startActivity(homeIntent);
				
	}
}
