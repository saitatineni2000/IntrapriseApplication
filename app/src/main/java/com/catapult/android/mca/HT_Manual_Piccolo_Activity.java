package com.catapult.android.mca;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HT_Manual_Piccolo_Activity extends Activity {
    
	Button saveBtn;
    Button cancelBtn;
    String authToken = null;
	String dob = null, patId = null, patName = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ht_manual_piccolo);
		
        //manualBtn = (Button)findViewById(R.id.manualBtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        
        Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
        
	}
	
	
	public void save(View view) {
		System.out.println("Inside HT_Manual_Piccolo_Activity:save");
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
		startActivity(homeIntent);
		
		/*if (datePickerText.getText().toString().trim().equals(dob.trim())) {
			System.out.println("We have a match!");
			startActivity(homeIntent);
		}
		else {
			System.out.println("OOPS!");
			final Dialog dialog = new Dialog(PatientVerificationActivity.this);
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Patient Verification Alert");
            TextView sampleText = (TextView) dialog.findViewById(R.id.dialog_text);
            sampleText.setText("Invalid Patient birth date.  Please try again");
            
            Button okBtn = (Button) dialog.findViewById(R.id.btn_ok);
            dialog.show();
	        okBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dialog.dismiss();
		        };
	        });
		}*/
	}
	
	@Override
	public void onBackPressed() {
		System.out.println("Back pressed");
	}
}
