package com.catapult.android.mca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class HT_Vaccine_Yes_No_Activity extends Activity {
	String authToken = null;
	String dob, patId, patName = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaccine__yes__no);
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
	}
	
	
	public void vaccineYes(View view) {
		System.out.println("Inside HT_Vaccine_Yes_No_Activity:Yes");
		Intent homeIntent = new Intent(getApplicationContext(),
				HT_Vaccine_Questionnaire_Activity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
		
		startActivity(homeIntent);
		
	}
    
	/**
	 * 
	 * @param view
	 */
    public void vaccineNo(View view) {
		System.out.println("Inside HT_Vaccine_Yes_No_Activity:No");
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
			
	}
    
    @Override
	public void onBackPressed() {
		System.out.println("Back pressed");
	}
}
