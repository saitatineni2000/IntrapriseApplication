package com.catapult.android.mca;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class HT_Vaccine_Complete_Activity extends Activity  implements OnItemSelectedListener{

	Spinner spinner;
	String authToken = null;
	String dob = null, patId = null, patName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ht_vaccine_complete);
		Intent intent = getIntent();
		byte[] array = intent.getByteArrayExtra("byteArray");
		System.out.println("byte array received from patient signature screen "
				+ array.length);

		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
		// Spinner element
        spinner = (Spinner) findViewById(R.id.vaccine_details_row1_admin_spinner);
       
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        
        List<String> piccoloIds = new ArrayList<String>();
        piccoloIds.add("Jason Mason");
        piccoloIds.add("Sally Jones");
        piccoloIds.add("Heather Miller");
		loadSpinnerData(piccoloIds);

	}

	public void nextScreen(View view) {
		System.out.println("Inside HT_Vaccine_Complete_Activity");
		Intent homeIntent = new Intent(getApplicationContext(),
				HT_Additional_Services_Activity.class);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("dateOfBirth", dob);
		homeIntent.putExtra("patientId", patId);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);

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
	
    @Override
	public void onBackPressed() {
		System.out.println("Back pressed");
	}
    
}
