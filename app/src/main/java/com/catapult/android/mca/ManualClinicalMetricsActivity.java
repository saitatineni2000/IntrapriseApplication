package com.catapult.android.mca;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ManualClinicalMetricsActivity extends Activity {
	
	String authToken = null;
	String dob, patId, patName = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_clinical_metrics);
		
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
		populateheartRateSpinner();
		populateWeightSpinner();
		populateHeightSpinner();
		populateAbdominalSpinner();
		
	}
	
	/**
	 * 
	 */
	private void populateheartRateSpinner() {
		
		List<Integer> heartRateValues = new ArrayList<Integer>();
		for (int x = 30; x <200; x++) {
			heartRateValues.add(x);
		}
		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.heart_rate_spinner);

		// Application of the Array to the Spinner
		ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this,   android.R.layout.simple_spinner_item, heartRateValues);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setSelection(50);
	}
	
	/**
	 * 
	 */
	private void populateWeightSpinner() {
		
		List<Integer> weightValues = new ArrayList<Integer>();
		for (int x = 40; x <400; x++) {
			weightValues.add(x);
		}
		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.weight_spinner);

		// Application of the Array to the Spinner
		ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this,   android.R.layout.simple_spinner_item, weightValues);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setSelection(140);
	}
	
	private void populateHeightSpinner() {
		
		List<Integer> heightValues = new ArrayList<Integer>();
		for (int x = 1; x <7; x++) {
			heightValues.add(x);
		}
		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.height_spinner);

		// Application of the Array to the Spinner
		ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this,   android.R.layout.simple_spinner_item, heightValues);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setSelection(5);
	}
	
	/**
	 * 
	 */
	private void populateAbdominalSpinner() {
		
		List<Integer> heightValues = new ArrayList<Integer>();
		for (int x = 22; x <50; x++) {
			heightValues.add(x);
		}
		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.abdominal_circumference_spinner);

		// Application of the Array to the Spinner
		ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this,   android.R.layout.simple_spinner_item, heightValues);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setSelection(8);
	}
	
	/**
	 * 
	 * @param view
	 */
	public void close(View view) {
		System.out.println("Inside ManualClinicalMetricsActivity");
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
			
	}
	
	/**
	 * 
	 * @param view
	 */
	public void completeManualMetrics (View view) {
		
		System.out.println("Inside completeManualMetrics");
		Intent homeIntent = new Intent(getApplicationContext(),
				HT_Vaccine_Yes_No_Activity.class);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
		
	}
	
}
