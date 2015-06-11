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
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private EditText usernameET = null;
	private EditText passwordET = null;
	private Button login;
	// Progress Dialog Object
	ProgressDialog prgDialog;
	// Error Message TextView Object
	TextView errorMsg;
	String authToken = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		final TextView text2 = (TextView) findViewById(R.id.textView2);
		
		if (text2 != null) System.out.println("Not null");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		System.out.println("Username = " + usernameET);
		String userId = usernameET.toString();
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
		// params.put("package",
		// "{\"type\":\"auth-token-request\",\"username\":\"michael@catapulthealth.com\",\"password\":\"lp3tMJIP+5StMzv5XOKEWzZ4b/k=\"}");
		params.put("","");
		// When Email Edit View and Password Edit View have values other than
		// Null
		// params.put("username", "nkapur@intraprise.com");
		// Put Http parameter password with value of Password Edit Value control
		// params.put("password", "test123");
		invokeWS(params);

	}
	
	public void forgotPassword(View view) {
		System.out.println("Inside MainActivity:hello");
		Intent homeIntent = new Intent(getApplicationContext(),
				ResetPasswordActivity.class);
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
		// package={"type":"auth-token-request","username":"michael@catapulthealth.com","password":"lp3tMJIP+5StMzv5XOKEWzZ4b/k="}
		AsyncHttpClient client = new AsyncHttpClient();
		JSONObject jsonParams = new JSONObject();

		/*
		 * try { jsonParams.put("notes", "Test api support"); StringEntity
		 * entity = new StringEntity(jsonParams.toString());
		 * client.post(context, restAPIUrl, entity, "application/json",
		 * responseHandler); } catch (Exception e) {e.printStackTrace();}
		 */
		HttpEntity entity = null;

		try {
			// jsonParams.put("package",
			// "%7B%22type%22%3A%22auth-token-request%22%2C%22username%22%3A%22michael%40catapulthealth.com%22%2C%22password%22%3A%22lp3tMJIP%2B5StMzv5XOKEWzZ4b%2Fk%3D%22%7D");
			// entity = new StringEntity(jsonParams.toString(), "UTF-8");
			//String payload = "{\"type\":\"auth-token-request\",\"username\":\"michael@catapulthealth.com\",\"password\":\"lp3tMJIP+5StMzv5XOKEWzZ4b/k=\"}";
			String payload = "{\"type\":\"auth-token-request\",\"username\":\"michael@catapulthealth.com\",\"password\":\"amhlSfzCZ94ca4AG+KKsRK0IJg8=\"}";
			payload = URLEncoder.encode(payload, "utf-8");
			/*entity = new StringEntity(
					"package="
							+ "%7B%22type%22%3A%22auth-token-request%22%2C%22username%22%3A%22michael%40catapulthealth.com%22%2C%22password%22%3A%22lp3tMJIP%2B5StMzv5XOKEWzZ4b%2Fk%3D%22%7D",
					"UTF-8");*/
			entity = new StringEntity("package=" + payload, "UTF-8");
		} catch (IllegalArgumentException e) {
			System.err.println("HTTP StringEntity: IllegalArgumentException");
			return;
		} catch (UnsupportedEncodingException e) {
			System.err
					.println("HTTP StringEntity: UnsupportedEncodingException");
			return;
		} /*
		 * catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); System.err.println(e.getMessage()); }
		 */

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
                                        Toast.LENGTH_SHORT).show();
								// Navigate to Home screen
								navigatetoHomeActivity();
							}
							// Else display error message
							else {
								errorMsg.setText(json.getString("error_msg"));
								Toast.makeText(getApplicationContext(),
										json.getString("error_msg"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Toast.makeText(
									getApplicationContext(),
									"Error Occured [Server's JSON response might be invalid]!",
                                    Toast.LENGTH_SHORT).show();
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
                                    Toast.LENGTH_SHORT).show();
						}
						// When Http response code other than 404, 500
						else {
							Toast.makeText(
									getApplicationContext(),
									"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
                                    Toast.LENGTH_SHORT).show();
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
	/*public void navigatetoHomeActivity() {
		Intent homeIntent = new Intent(getApplicationContext(),
				PatientVerificationActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}*/
	
	/**
	 * Method which navigates from Login Activity to Team Lead Home Activity
	 */
	public void navigatetoHomeActivity() {
		Intent homeIntent = new Intent(getApplicationContext(),
			TeamLeadHome.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
