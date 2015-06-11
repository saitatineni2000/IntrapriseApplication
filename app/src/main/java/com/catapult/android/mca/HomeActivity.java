package com.catapult.android.mca;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.catapult.android.mca.PreClinicActivity.DatePickerFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

/**
 * 
 * Home Screen Activity
 */
public class HomeActivity extends Activity implements OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("in here");
		setContentView(R.layout.home);
		/*
		 * // Create a progress bar to display while the list loads ProgressBar
		 * progressBar = new ProgressBar(this); progressBar.setLayoutParams(new
		 * LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
		 * Gravity.CENTER)); progressBar.setIndeterminate(true);
		 * getListView().setEmptyView(progressBar);
		 * 
		 * // Must add the progress bar to the root of the layout ViewGroup root
		 * = (ViewGroup) findViewById(android.R.id.content);
		 * root.addView(progressBar);
		 * 
		 * // For the cursor adapter, specify which columns go into which views
		 * String[] fromColumns = { ContactsContract.Data.DISPLAY_NAME }; int[]
		 * toViews = { android.R.id.text1 }; // The TextView in //
		 * simple_list_item_1
		 * 
		 * // Create an empty adapter we will use to display the loaded data. //
		 * We pass null for the cursor, then update it in onLoadFinished()
		 * mAdapter = new SimpleCursorAdapter(this,
		 * android.R.layout.simple_list_item_1, null, fromColumns, toViews, 0);
		 * setListAdapter(mAdapter);
		 * 
		 * // Prepare the loader. Either re-connect with an existing one, // or
		 * start a new one. getLoaderManager().initLoader(0, null, this);
		 */
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

	public void gotoPreClinicScreen(View v) {
		Intent homeIntent = new Intent(getApplicationContext(),
				PreClinicActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	public void gotoClinicScreen(View v) {
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicPatientViewActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}
	
	public void gotoPatientDetailsScreen(View v) {
		Intent homeIntent = new Intent(getApplicationContext(),
				PatientDetailsActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

}
