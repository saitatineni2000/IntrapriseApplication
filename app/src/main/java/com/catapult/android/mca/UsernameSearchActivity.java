package com.catapult.android.mca;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UsernameSearchActivity extends Activity {
	
	ProgressDialog prgDialog;
	// Error Message TextView Object
	TextView errorMsg;
	private EditText usernameET = null;
	private String username = null;
	private String authToken = null;
	private int securityQuestionId ;
	private String securityQuestionText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_username_search);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			authToken = extras.getString("authToken");
		}
		errorMsg = (TextView)findViewById(R.id.username_error);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.username_search, menu);
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
	
	public void searchUser(View view) {
		System.out.println("Inside UsernameSearchActivity:searchUser");
		// Get Email Edit View Value
		usernameET = (EditText) findViewById(R.id.editText1);
		System.out.println("Username = " + usernameET.getText());
		username = usernameET.getText().toString();
		
		// Instantiate Progress Dialog object
		prgDialog = new ProgressDialog(this);
		// Set Progress Dialog Text
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);
		
		invokeWS();

	}
	
	public void hello(View view) {
		System.out.println("Inside UsernameSearchActivity");
		Intent homeIntent = new Intent(getApplicationContext(),
				PreClinicActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	/**
	 * Method that performs RESTful web service invocations
	 * 
	 * @param params
	 */
	public void invokeWS() {
		// Show Progress Dialog
		prgDialog.show();
		String restAPIUrl = "http://10.100.100.35/CatapultStaging/gateway/api/v1/api-gateway.aspx";

		// RESTful Web Service call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		HttpEntity entity = null;
			
		try {
			String payload = "{\"type\":\"dbo-request\",\"dboName\":\"LookupStaff_MCA\",\"username\":\""+username+"\",\"authToken\":\""+authToken+"\"}";
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
		
		System.out.println("About to make a POST call to search username");
		
		client.post(getApplicationContext(), restAPIUrl, entity,
				"application/x-www-form-urlencoded",
				new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						boolean staffExists = false;
						// Hide Progress Dialog
						prgDialog.hide();
						try {
							System.out.println("Server Response = " + response);
							// JSON Object
							JSONArray obj = new JSONArray(response);
							// When the JSON response has status boolean value
							// assigned with true
							
							JSONObject json = null;
	                         System.out.println("Retrieved "+obj.length() + " json objects");
	                         for (int x = 0; x < obj.length(); x++) {
	                        	 json =  (JSONObject)obj.get(x);
	                        	 staffExists = json.getBoolean("StaffExists");
	                        	 securityQuestionText = json.getString("SecurityQuestionText");
	                        	 securityQuestionId = json.getInt("SecurityQuestionId");
	                        	 break;
	                         }
	                         
							if (staffExists) {
								Toast.makeText(getApplicationContext(),
										"Username exists ",
										5).show();
								// Navigate to Home screen
								navigatetoResetPassword2Activity(username);
							}
							
							// Else display error message
							else {
								errorMsg.setText("Username does not exist");
								Toast.makeText(getApplicationContext(),
										"Username does not exist",
										10).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Toast.makeText(
									getApplicationContext(),
									"Error Occured [Server's JSON response might be invalid]!",
									5).show();
							e.printStackTrace();

						}
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
	 * Method which navigates from Login Activity to Home Activity
	 */
	public void navigatetoResetPassword2Activity(String username) {
		Intent homeIntent = new Intent(getApplicationContext(),
			PasswordResetActivity2.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("username", username);
		homeIntent.putExtra("securityQuestionId", securityQuestionId);
		homeIntent.putExtra("securityQuestionText", securityQuestionText);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
	}
}
