package com.catapult.android.mca;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class HT_Additional_Services_Activity extends Activity implements OnItemSelectedListener{
	
	String authToken = null;
	String dob, patId, patName = null;
	Spinner spinner;
	int positive, negative;
	Button positiveBtn = null, negativeBtn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ht_additional_services);
		
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
		
		// Spinner element
        spinner = (Spinner) findViewById(R.id.vaccine_details_row3_admin_spinner);
       
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        
        List<String> piccoloIds = new ArrayList<String>();
        piccoloIds.add("Jason Mason");
        piccoloIds.add("Sally Jones");
        piccoloIds.add("Heather Miller");
		loadSpinnerData(piccoloIds);
		
		positiveBtn = (Button) findViewById(R.id.positive);
		negativeBtn = (Button) findViewById(R.id.negative);
		negative = negativeBtn.getSolidColor();
		negative = positiveBtn.getSolidColor();
	}
	
	
	/**
	 * 
	 * @param view
	 */
    public void positive(View view) {
		System.out.println("Inside HT_Additional_Services_Activity: positive");
		/*Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dob", dob);
		startActivity(homeIntent);*/
		
		positiveBtn = (Button) findViewById(R.id.positive);
		positiveBtn.setBackground(getResources().getDrawable(R.color.lime_green));
		negativeBtn = (Button) findViewById(R.id.negative);
		negativeBtn.setBackgroundColor(negative);
	}
    
    /**
	 * 
	 * @param view
	 */
    public void negative(View view) {
		System.out.println("Inside HT_Additional_Services_Activity: negative");
		/*Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);*/
		positiveBtn.setBackgroundColor(positive);
		negativeBtn.setBackgroundColor(getResources().getColor(R.color.lime_green));
			
	}
    
    @Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();
 
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub	
	}
	
	/**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData(List<String> deviceIds) {
        
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, deviceIds);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    
    /**
     * 
     * @param view
     */
    public void nextScreen(View view) {
		System.out.println("Inside HT_Additional_Services_Activity: nextScreen");
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
		
		startActivity(homeIntent);
	}
	
    @Override
	public void onBackPressed() {
		System.out.println("HT_Additional_Services_Activity - Back button pressed");
	}
    

	
}
