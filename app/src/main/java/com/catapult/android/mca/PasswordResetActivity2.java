package com.catapult.android.mca;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PasswordResetActivity2 extends Activity implements OnItemSelectedListener{
	
	// Spinner element
    Spinner spinner;
    private String username = null;
	private int securityQuestionId ;
	private String securityQuestionText = null;
	private String authToken = null;
	// Progrss Dialog Object
	ProgressDialog prgDialog;
	// Error Message TextView Object
	TextView errorMsg;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_reset_activity_2);
		
		// Spinner element
        spinner = (Spinner) findViewById(R.id.spinner1);
       
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        
        Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    username = extras.getString("username");
		    securityQuestionText = extras.getString("securityQuestionText");
		    securityQuestionId = extras.getInt("securityQuestionId");
		    authToken = extras.getString("authToken");
		}
		
		TextView usernameView = (TextView)findViewById(R.id.textView4);
	    usernameView.setText(username);
	        
		
		List<String> securityQuestions = new ArrayList<String>();
		securityQuestions.add(securityQuestionText);
		loadSpinnerData(securityQuestions);
		errorMsg = (TextView)findViewById(R.id.reset_error);
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password_reset_activity2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData(List<String> labels) {
        
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    
    public void resetPassword(View view) {
		System.out.println("Inside MainActivity:loginUser");
		
		// Instantiate Progress Dialog object
		prgDialog = new ProgressDialog(this);
		// Set Progress Dialog Text
		prgDialog.setMessage("Please wait...");
		
		prgDialog.setCancelable(false);
		
		invokeWS();

	}
	
	public void hello(View view) {
		System.out.println("Inside MainActivity:hello");
		Intent homeIntent = new Intent(getApplicationContext(),
				PreClinicActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	/**
	 * Method that performs RESTful webservice invocations
	 * 
	 * @param params
	 */
	public void invokeWS() {
		// Show Progress Dialog
		prgDialog.show();
		String restAPIUrl = "http://10.100.100.35/CatapultStaging/gateway/api/v1/api-gateway.aspx";

		// RESTful web Service call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		HttpEntity entity = null;
		///package={"type":"dbo-request","dboName":"SetPassword","username":"michael@catapulthealth.com", "authToken":"408ab8b028f25e33bf9f36cad17e0801","newUser":"","securityQuestionId":"1","securityQuestionAnswer":"fluffy1","newPassword":"C@tapult1"}
		try {
			String payload = "{\"type\":\"dbo-request\",\"dboName\":\"SetPassword_MCA\",\"username\":\""+username+"\",\"authToken\":\""+authToken+"\"}"
					+ ",\"newUser\":0,\"securityQuestionId\":"+securityQuestionId
					+ ",\"securityQuestionAnswer\":\""+securityQuestionText+"\",\"newPassword\":\""+"test123"+"\"";
			payload = URLEncoder.encode(payload, "utf-8");
			entity = new StringEntity("package=" + payload, "UTF-8");
		} catch (IllegalArgumentException e) {
			System.err.println("HTTP StringEntity: IllegalArgumentException");
			return;
		} catch (UnsupportedEncodingException e) {
			System.err
					.println("HTTP StringEntity: UnsupportedEncodingException");
			return;
		} 
		
		System.out.println("About to make a POST call to reset password");
		
		client.post(getApplicationContext(), restAPIUrl, entity,
				"application/x-www-form-urlencoded",
				new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						boolean resetSuccessful = false;
						// Hide Progress Dialog
						prgDialog.hide();
						try {
							System.out.println("Server Response = " + response);
							// JSON Object
							JSONArray obj = new JSONArray(response);
							// When the JSON response has status boolean value
							// assigned with true
							String authToken = null;
							JSONObject json = null;
	                         System.out.println("Retrieved "+obj.length() + " json objects");
	                         for (int x = 0; x < obj.length(); x++) {
	                        	 json =  (JSONObject)obj.get(x);
	                        	 resetSuccessful = json.getBoolean("saveSuccessful");
	                        	 /*securityQuestionText = json.getString("SecurityQuestionText");
	                        	 securityQuestionId = json.getInt("SecurityQuestionId");*/
	                        	 break;
	                         }
	                         
							if (resetSuccessful) {
								Toast.makeText(getApplicationContext(),
										"Password successfully reset",
										10).show();
								// Navigate to Home screen
								navigatetoHTDashboardActivity(username);
								return;
							}
							
							// Else display error message
							else {
								errorMsg.setText("Password could not be reset");
								Toast.makeText(getApplicationContext(),
										"Password could not be reset",
										10).show();
							}
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							/*Toast.makeText(
									getApplicationContext(),
									"Error Occured [Server's JSON response might be invalid]!",
									5).show();*/
							e.printStackTrace();

						}
						navigatetoHTDashboardActivity(username);
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						// Hide Progress Dialog
						prgDialog.hide();
						// When Http response code is '404'
						System.out.println("StatusCode = " + statusCode);
						if (statusCode == 404) {
							Toast.makeText(getApplicationContext(),
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code is '500'
						else if (statusCode == 500) {
							Toast.makeText(getApplicationContext(),
									"Something went wrong at server end",
									5).show();
						}
						// When Http response code other than 404, 500
						else {
							Toast.makeText(
									getApplicationContext(),
									"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
									5).show();
						}
					}
				});

		// TODO delete
		// Hide Progress Dialog
		prgDialog.hide();		
		
	}

	/**
	 * Method which navigates from Password Reset Activity to TL's Dashboard Activity
	 */
	public void navigatetoHTDashboardActivity(String username) {
		Intent homeIntent = new Intent(getApplicationContext(),
			PatientVerificationActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("username", username);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
	}
}
