package com.catapult.android.mca;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.catapult.android.model.Clinic;
import com.catapult.android.model.Patient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PatientDetailInfoActivity extends Activity {
	private String authToken = null;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_detail_info);
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		int patId = extras.getInt("patientId");
		showPatientDetail(authToken, patId);
	}

	/**
	 * 
	 * @param authToken
	 */
	private void showPatientDetail(String authToken, int patientId) {
		String restAPIUrl = "http://10.100.100.35/CatapultStaging/gateway/api/v1/api-gateway.aspx";
		AsyncHttpClient client = new AsyncHttpClient();
		HttpEntity entity = null;
		// package={"type":"dbo-request","dboName":"FetchPatients","firstName":"%",
		// "authToken":"78a498bcf2a13591989d57161d609295","patientId":"671233","lastName":"%","companyName":"","DOB":""}
		try {
			String payload = "{\"type\":\"dbo-request\",\"dboName\":\"FetchPatients\",\"patientId\":\""
					+ patientId
					+ "\", \"authToken\":\""
					+ authToken
					+ "\",\"lastName\":\"%\",\"companyName\":\"\",\"DOB\":\"\"}";

			payload = URLEncoder.encode(payload, "utf-8");
			entity = new StringEntity("package=" + payload, "UTF-8");
			System.out.println("payload = " + payload);

		} catch (IllegalArgumentException e) {
			System.err.println("HTTP StringEntity: IllegalArgumentException");
			Toast.makeText(
					getApplicationContext(),
					"Error Occured [HTTP StringEntity: IllegalArgumentException]!",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.err
					.println("HTTP StringEntity: UnsupportedEncodingException");
			Toast.makeText(
					getApplicationContext(),
					"Error Occured [HTTP StringEntity: UnsupportedEncodingException]!",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		System.out
				.println("About to make a POST call to retrieve Patient for authToken ="
						+ authToken + ", patientId = " + patientId);

		client.post(getApplicationContext(), restAPIUrl, entity,
				"application/x-www-form-urlencoded",
				new AsyncHttpResponseHandler() {
					final String PatientId = "PatientId";
					final String First_Name = "First_Name";
					final String Last_Name = "Last_Name";
					final String Middle_Initial = "Middle_Initial";
					final String DOB = "DOB";
					final String Gender = "Gender";
					final String Email_Address = "Email_Address";
					final String Ethnicity_ID = "Ethnicity_ID";
					final String Hispanic_Latino = "Hispanic_Latino";
					final String Language_ID = "Language_ID";
					final String Address_1 = "Address_1";
					final String Address_2 = "Address_2";
					final String City = "City";
					final String State = "State";
					final String Postal_Code = "Postal_Code";
					final String Home_Phone = "Home_Phone";
					final String Cell_Phone = "Cell_Phone";
					final String Insurance_Plan_ID = "Insurance_Plan_ID";
					final String Insurance_Group_ID = "Insurance_Group_ID";
					final String Insurance_Member_ID = "Insurance_Member_ID";
					final String Company_Name = "Company_Name";
					final String SSN = "SSN";
					final String Insurance_Relation = "Insurance_Relation";
					final String Insurance_Effective_Date = "Insurance_Effective_Date";
					final String Insurance_First_Name = "Insurance_First_Name";
					final String Insurance_Last_Name = "Insurance_Last_Name";
					final String Employee_Id = "Employee_Id";

					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						// Hide Progress Dialog
						Patient p = null;
						try {
							System.out.println("Server Response = " + response);
							// JSON Object
							JSONArray obj = new JSONArray(response);
							if (obj.length() == 0) {
								System.out
										.println("No patients found in the Clinic");
								return;
							}

							// When the JSON response has status boolean value
							// assigned with true
							String authToken = null;
							JSONObject c = null;
							System.out.println("Retrieved " + obj.length()
									+ " json objects");

							for (int x = 0; x < obj.length(); x++) {
								c = (JSONObject) obj.get(x);
								System.out.println(c.toString());

								// Storing each json item in variable
								p = new Patient();
								p.setFirstName(c.getString(First_Name));
								p.setLastName(c.getString(Last_Name));
								p.setMiddle_Initial(c.getString(Middle_Initial));
								p.setDOB(c.getString(DOB));
								p.setGender(c.getString(Gender));
								p.setEmail_Address(c.getString(Email_Address));
								p.setLanguage_ID(c.getString(Language_ID));
								p.setAddress_1(c.getString(Address_1));
								p.setAddress_2(c.getString(Address_2));
								p.setCity(c.getString(City));
								p.setState(c.getString(State));
								p.setPostal_Code(c.getString(Postal_Code));
								p.setHome_Phone(c.getString(Home_Phone));
								p.setCell_Phone(c.getString(Cell_Phone));
								p.setInsurance_Plan_ID(c
										.getString(Insurance_Plan_ID));
								p.setInsurance_Group_ID(c
										.getString(Insurance_Group_ID));
								p.setInsurance_Member_ID(c
										.getString(Insurance_Member_ID));
								p.setCompany_Name(c.getString(Company_Name));
								p.setSSN(c.getString(SSN));
								p.setInsurance_Relation(c
										.getString(Insurance_Relation));
								p.setInsurance_Effective_Date(c
										.getString(Insurance_Effective_Date));
								p.setInsurance_First_Name(c
										.getString(Insurance_First_Name));
								p.setInsurance_Last_Name(c
										.getString(Insurance_Last_Name));
								p.setEmployee_Id(c.getString(Employee_Id));

								System.out.println(p.toString());
							}

							// showActivity();

							if (p != null) {
								EditText e = (EditText) findViewById(R.id.first_name_edit_text);
								e.setText(p.getFirstName());

								e = (EditText) findViewById(R.id.middle_name_edit_text);
								e.setText(p.getMiddle_Initial());

								e = (EditText) findViewById(R.id.last_name_edit_text);
								e.setText(p.getLastName());

								e = (EditText) findViewById(R.id.dob_edit_text);
								e.setText(p.getDOB());

								e = (EditText) findViewById(R.id.email_edit_text);
								e.setText(p.getEmail_Address());

								e = (EditText) findViewById(R.id.mailing_address1_edit_text);
								e.setText(p.getAddress_1());

								e = (EditText) findViewById(R.id.mailing_address2_edit_text);
								e.setText(p.getAddress_2());

								e = (EditText) findViewById(R.id.city_edit_text);
								e.setText(p.getCity());

								e = (EditText) findViewById(R.id.primary_phone_edit_text);
								e.setText(p.getHome_Phone());

								e = (EditText) findViewById(R.id.secondary_phone_edit_text);
								e.setText(p.getCell_Phone());

								e = (EditText) findViewById(R.id.ssn_edit_text);
								e.setText(p.getSSN());

								e = (EditText) findViewById(R.id.groupid_edit_text);
								e.setText(p.getInsurance_Group_ID());

								e = (EditText) findViewById(R.id.zipcode_edit_text);
								e.setText(p.getPostal_Code());

								e = (EditText) findViewById(R.id.effectivedate_edit_text);
								e.setText(p.getInsurance_Effective_Date());

								e = (EditText) findViewById(R.id.employeeid_edit_text);
								e.setText(p.getEmployee_Id());

								e = (EditText) findViewById(R.id.relationship_edit_text);
								e.setText(p.getInsurance_Relation());
							}

						} catch (JSONException e) {
							Toast.makeText(
									getApplicationContext(),
									"Error Occured [Server's JSON response might be invalid]!",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();

						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),
									"Unknown Error Occured!", Toast.LENGTH_LONG)
									.show();
							e.printStackTrace();
						}
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						// Http response code is '404'
						System.out.println("StatusCode = " + statusCode);
						if (statusCode == 404) {
							Toast.makeText(getApplicationContext(),
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						}
						// Http response code is '500'
						else if (statusCode == 500) {
							Toast.makeText(getApplicationContext(),
									"Something went wrong at server end",
									Toast.LENGTH_LONG).show();
						}
						// Http response code other than 404, 500
						else {
							Toast.makeText(
									getApplicationContext(),
									"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
									Toast.LENGTH_LONG).show();
						}
					}
				});
	}
	
	public void cancel(View v) {
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
	}

}
