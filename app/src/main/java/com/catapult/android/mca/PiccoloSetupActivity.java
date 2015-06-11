package com.catapult.android.mca;

import java.util.ArrayList;
import java.util.List;

import com.catapult.android.mca.PreClinicActivity.DatePickerFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PiccoloSetupActivity extends Activity  implements OnItemSelectedListener {
	
	// Spinner element
    Spinner spinner;
	String authToken = null;
	String dob = null;
	String patId = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_piccolo_setup);
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		
		EditText text = (EditText) findViewById(R.id.patientIDEditBox);
		text.setText(patId);
		
		text = (EditText) findViewById(R.id.DOBEditBox);
		text.setText(dob);
		
		// Spinner element
        spinner = (Spinner) findViewById(R.id.piccoloIDSpinner);
       
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        
        List<String> piccoloIds = new ArrayList<String>();
        piccoloIds.add("Select One");
        piccoloIds.add("1123345");
        piccoloIds.add("2212234");
		loadSpinnerData(piccoloIds);
		
		
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
    public void piccolo_beginAnalysis(View view) {
		System.out.println("Inside PiccoloSetupActivity_beginAnalysis");
		Intent homeIntent = new Intent(getApplicationContext(),
				A1cActivity.class);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("dateOfBirth",dob);
		homeIntent.putExtra("patientId",patId);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
		
	}
    
    /**
     * 
     * @param view
     */
    public void close(View view) {
		System.out.println("Inside PiccoloSetupActivity:Close");
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
		
		
	}
}
