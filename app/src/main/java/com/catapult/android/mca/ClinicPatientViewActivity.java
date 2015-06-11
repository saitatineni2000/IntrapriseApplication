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

import com.catapult.android.model.FruitAdapter;
import com.catapult.android.model.FruitClass;
import com.catapult.android.model.Patient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClinicPatientViewActivity extends Activity {
	// Progress Dialog Object
	ProgressDialog prgDialog;
	// Error Message TextView Object
	TextView errorMsg;
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clinic_patient_view);

		int i = 0;
		List<Patient> patientList = getPatientList(null);

		System.out.println("Done onCreate");

	}

	public List<Patient> getPatientList(RequestParams params) {
		prgDialog = new ProgressDialog(this);
		// Set Progress Dialog Text
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);
		// Show Progress Dialog
		prgDialog.show();

		String restAPIUrl = "http://10.100.100.35/CatapultStaging/gateway/api/v1/api-gateway.aspx";

		AsyncHttpClient client = new AsyncHttpClient();

		HttpEntity entity = null;
		final List<Patient> patients = new ArrayList<Patient>();

		try {

			String payload = "{\"type\":\"dbo-request\",\"dboName\":\"FetchPatientsByClinicId\",\"clinicId\":\"a0CC000000T3pfMMAR\", \"authToken\":\"7b5b8db912c9b1c9b41489fb610a6217\"}";

			payload = URLEncoder.encode(payload, "utf-8");
			entity = new StringEntity("package=" + payload, "UTF-8");
			System.out.println("payload = " + entity);

		} catch (IllegalArgumentException e) {
			System.err.println("HTTP StringEntity: IllegalArgumentException");
			return null;
		} catch (UnsupportedEncodingException e) {
			System.err
					.println("HTTP StringEntity: UnsupportedEncodingException");
			return null;
		}

		System.out
				.println("About to make a POST call to retrieve Patient List");
		client.post(getApplicationContext(), restAPIUrl, entity,
				"application/x-www-form-urlencoded",
				new AsyncHttpResponseHandler() {
					final String PATIENT_ID = "PatientId";
					final String HEALTHEVAL_ID = "HealthEvalId";
					final String HEALTHEVAL_STATUS_ID = "HealthEvalStatusId";
					final String STATUS = "Status";
					final String LAST_NAME = "LastName";
					final String FIRST_NAME = "FirstName";
					final String DOB = "DOB";
					final String START_TIME = "StartTime";
					final String ELAPSED_TIME = "ElapsedTime";
					final String OUTCOMES_PROGRAM_ID = "OutcomesProgramId";

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
							patients.add( new Patient("PatientId     ", "HealthEvalId",
										"StatusId",
										"  Status", "LastName", null, null, null, null, null));
							for (int x = 0; x < obj.length(); x++) {
								c = (JSONObject) obj.get(x);
								System.out.println(c.toString());

								// Storing each json item in variable
								Patient p = new Patient(Integer.toString(c
										.getInt(PATIENT_ID)), Integer
										.toString(c.getInt(HEALTHEVAL_ID)),
										Integer.toString(c
												.getInt(HEALTHEVAL_STATUS_ID)),
										c.getString(STATUS), c
												.getString(LAST_NAME), c
												.getString(FIRST_NAME), c
												.getString(DOB), c
												.getString(START_TIME), c
												.getString(ELAPSED_TIME), c
												.getString(OUTCOMES_PROGRAM_ID));

								patients.add(p);
								System.out.println(p.toString());
							}
							
							listview = (ListView) findViewById(R.id.main_list_view);
							FruitAdapter adapter = new FruitAdapter(getApplicationContext(),
									R.layout.row_layout);
							int i = 0;
							System.out.println("Clinic has " + patients.size() + " patients");

							if (patients != null && patients.size() > 0) {
								for (Patient patient : patients) {
									System.out.println(patient.getPatientId() + " --- " + patient.getStatus() + " --- "  + patient.getLastName());
									FruitClass p = new FruitClass(patient.getPatientId()+"       ", patient.getStatus(), patient.getLastName(), null);
									adapter.add(p);
									/*i++;
									if (i == 3)
										break;*/
								}
							}
							listview.setAdapter(adapter);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Toast.makeText(
									getApplicationContext(),
									"Error Occured [Server's JSON response might be invalid]!",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();

						} catch (Exception e) {
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
									Toast.LENGTH_LONG).show();
						}
						// When Http response code other than 404, 500
						else {
							Toast.makeText(
									getApplicationContext(),
									"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
									Toast.LENGTH_LONG).show();
						}
					}
				});

		// TODO delete
		// Hide Progress Dialog

		prgDialog.hide();

		return patients;
	}
}
