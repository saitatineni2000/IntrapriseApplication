package com.catapult.android.mca;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HT_Vaccine_Questionnaire_Activity extends Activity {
	String authToken = null;
	String dob, patId, patName = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ht_vaccine_questionnaire);
		View decorView = getWindow().getDecorView();
		// Hide both the navigation bar and the status bar.
		// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
		// a general rule, you should design your app to hide the status bar whenever you
		// hide the navigation bar.
		int uiOptions = 
		              View.SYSTEM_UI_FLAG_FULLSCREEN
		              | View.SYSTEM_UI_FLAG_IMMERSIVE;
		decorView.setSystemUiVisibility(uiOptions);
		
		/*int uiOptions =View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_LOW_PROFILE
	            | View.SYSTEM_UI_FLAG_IMMERSIVE;*/
		
		/*ActionBar actionBar = getActionBar();
		actionBar.hide();*/
		
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
	}
	
	public void nextScreen(View view) {
		System.out.println("Inside HT_Vaccine_Yes_No_Activity:Yes");
		Intent homeIntent = new Intent(getApplicationContext(),
				HT_Vaccine_Pat_Signature_Activity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
		
		startActivity(homeIntent);
	}
	
	@Override
	public void onBackPressed() {
		System.out.println("Back pressed");
	}
}
