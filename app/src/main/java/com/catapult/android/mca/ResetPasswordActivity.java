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

public class ResetPasswordActivity extends Activity {
	ProgressDialog prgDialog;
	// Error Message TextView Object
	TextView errorMsg;
	private EditText usernameET = null;
	private EditText passwordET = null;
	String authToken = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reset_password, menu);
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
	
	public void login(View view) {
		System.out.println("Inside MainActivity:loginUser");
		// Get Email Edit View Value
		usernameET = (EditText) findViewById(R.id.editText1);
		System.out.println("Username = " + usernameET.getText().toString());
		String userId = usernameET.getText().toString();
		// Get Password Edit View Value
		// Find Password Edit View control by ID
		passwordET = (EditText) findViewById(R.id.editText2);
		// Instantiate Progress Dialog object
		prgDialog = new ProgressDialog(this);
		// Set Progress Dialog Text
		prgDialog.setMessage("Please wait...");
		// Set Cancelable as False
		prgDialog.setCancelable(false);
		String password = passwordET.getText().toString();
		
		// Instantiate Http Request Param Object
		RequestParams params = new RequestParams();
		invokeWS(params);

	}
	
	public void hello(View view) {
		System.out.println("Inside ResetPasswordActivity");
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
	public void invokeWS(RequestParams params) {
		// Show Progress Dialog
		prgDialog.show();
		String restAPIUrl = "http://10.100.100.35/CatapultStaging/gateway/api/v1/api-gateway.aspx";
		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		JSONObject jsonParams = new JSONObject();

		HttpEntity entity = null;

		try {
			
			String payload = "{\"type\":\"auth-token-request\",\"username\":\"michael@catapulthealth.com\",\"password\":\"amhlSfzCZ94ca4AG+KKsRK0IJg8=\"}";
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

		System.out.println("About to make a POST call");
		client.post(getApplicationContext(), restAPIUrl, entity,
				"application/x-www-form-urlencoded",
				new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
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
	                        	 authToken = json.getString("AuthToken");
	                        	 break;
	                         }
	                         
							if (authToken != null && authToken.length() > 0) {
								Toast.makeText(getApplicationContext(),
										"You are successfully logged in, auth token = "+authToken,
										5).show();
								// Navigate to Home screen
								navigatetoResetPassword2Activity();
							}
							// Else display error message
							else {
								errorMsg.setText(json.getString("error_msg"));
								Toast.makeText(getApplicationContext(),
										json.getString("error_msg"),
										5).show();
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
		//navigatetoResetPassword2Activity();
	}

	/**
	 * Method which navigates from Login Activity to Home Activity
	 */
	public void navigatetoResetPassword2Activity() {
		Intent homeIntent = new Intent(getApplicationContext(),
				UsernameSearchActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
	}
}
