package com.catapult.android.mca;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.catapult.android.model.Clinic;
import com.catapult.android.model.FruitAdapter;
import com.catapult.android.model.FruitClass;
import com.catapult.android.model.Patient;
import com.catapult.android.model.Patients;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

public class ClinicScheduleActivity extends Activity {

	ListView list, list_head;
	ArrayList<HashMap<String, String>> mylist, mylist_title;
	ListAdapter adapter_title, adapter;
	HashMap<String, String> map1, map2;
	String[] Appointment = { "7:00 am", "7:05 am", "7:10 am", "7:15 am",
			"7:20 am" };
	String[] type = { "A", "A", "0", "A", "A" };
	String[] Lastname = { "Smith", "Williamson", "", "Smith", "Thompson" };
	String[] Firstname = { "Mary", "Elizabeth", "", "James", " Henry" };
	String[] PatientId = { "187456", "183256", "", "135642", "135526" };
	String[] Language = { "E", "E", "", "S", "E" };
	int[] HQ = { R.drawable.circle_green, R.drawable.circle_green,
			R.drawable.circle_green, R.drawable.circle_green,
			R.drawable.circle_green };
	int[] HE = { R.drawable.circle_green, R.drawable.circle_green,
			R.drawable.circle_yellow, R.drawable.circle_green,
			R.drawable.circle_red };
	int[] NP = { R.drawable.circle_green, R.drawable.circle_yellow,
			R.drawable.circle_yellow, R.drawable.circle_green,
			R.drawable.circle_yellow };
	int[] FLUSHOT = { R.drawable.circle_grey, R.drawable.circle_green,
			R.drawable.circle_yellow, R.drawable.circle_yellow,
			R.drawable.circle_yellow, };
	int[] TDAP = { R.drawable.circle_green, R.drawable.circle_grey,
			R.drawable.circle_yellow, R.drawable.circle_green,
			R.drawable.circle_yellow };
	String[] Team = { "", "Todd", "", "Sally", "" };
	String[] Action = { "Start Px Workflow", "Start Px Workflow",
			"Start Px Workflow", "Start Px Workflow", "Start Px Workflow" };
	int[] Alerts = { 0, R.drawable.exclamation, 0, 0, R.drawable.exclamation };
	int textFontSize = 18;
	private int year;
	private int month;
	private int day;
	private DatePicker datePicker;
	EditText calEditText;
	TableLayout patients_table, patients_header_row;
	// Progress Dialog Object
	// ProgressDialog prgDialog;
	// Error Message TextView Object
	TextView errorMsg;
	ListView listview;
	static final int DATE_DIALOG_ID = 1;
	TextView clinicText;
	String authToken = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clinic_schedule);
		// list = (ListView) findViewById(R.id.listView2);
		// list_head = (ListView) findViewById(R.id.listView1);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// showActivity();

		// prgDialog = new ProgressDialog(this);
		// Set Progress Dialog Text
		// prgDialog.setMessage("Please wait...");
		// prgDialog.setCancelable(false);

		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		patients_table = (TableLayout) findViewById(R.id.patients_table);
		patients_header_row = (TableLayout) findViewById(R.id.patients_header_row);
		// clinic text pop-up
		clinicText = (TextView) findViewById(R.id.clinictext);
		clinicText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowDialog(authToken);
			}
		});

		ImageButton calImageBtn = (ImageButton) findViewById(R.id.calendar_image_button);
		calEditText = (EditText) findViewById(R.id.calendar_edit_box);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		calImageBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				onCreateDialog(DATE_DIALOG_ID).show();
			}
		});

		final Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		// updateDisplay();

		getPatientList(authToken);
	}

	/**
	 * 
	 */
	private void updateDisplay() {
		calEditText.setText(new StringBuilder().append(month + 1).append("/")
				.append(day).append("/").append(year).append(" "));
		datePicker.init(year, month, day, null);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, DateSetListener, year, month, day);
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(year, month, day);
			break;
		}
	}

	public DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int Year, int monthOfYear,
				int dayOfMonth) {
			year = Year;
			month = monthOfYear;
			day = dayOfMonth;
			updateDisplay();
		}
	};

	void ShowDialog(String authToken) {
		String restAPIUrl = "http://10.100.100.35/CatapultStaging/gateway/api/v1/api-gateway.aspx";
		AsyncHttpClient client = new AsyncHttpClient();
		HttpEntity entity = null;

		try {
			String payload = "{\"type\":\"dbo-request\",\"dboName\":\"FetchClinicDetails\",\"clinicId\":\"a0CC000000T3pfMMAR\", \"authToken\":\""
					+ authToken + "\"}";
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
				.println("About to make a POST call to retrieve Clinic Info using authToken ="
						+ authToken);

		client.post(getApplicationContext(), restAPIUrl, entity,
				"application/x-www-form-urlencoded",
				new AsyncHttpResponseHandler() {
					final String Name = "Name";
					final String Company = "Company";
					final String Address = "Address";
					final String City = "City";
					final String State = "State";
					final String PostalCode = "PostalCode";
					final String ServicesDelivered = "ServicesDelivered";
					final String FluShotEligibility = "FluShotEligibility";
					final String TdapEligibility = "TdapEligibility";

					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						// Hide Progress Dialog
						// prgDialog.hide();
						Clinic c = null;
						try {
							System.out.println("Server Response = " + response);
							// JSON Object
							JSONArray obj = new JSONArray(response);
							if (obj.length() == 0) {
								System.out.println("No Clinic");
								return;
							}

							// When the JSON response has status boolean value
							// assigned with true
							String authToken = null;
							JSONObject o = null;
							System.out.println("Retrieved " + obj.length()
									+ " json objects");

							for (int x = 0; x < obj.length(); x++) {
								o = (JSONObject) obj.get(x);
								System.out.println(o.toString());

								// Storing each json item in variable
								c = new Clinic();
								c.setName(o.getString(Name));
								c.setCompany(o.getString(Company));
								c.setAddress(o.getString(Address));
								c.setCity(o.getString(City));
								c.setState(o.getString(State));
								c.setZip(o.getString(PostalCode));
								c.setFluShotEligibility(o
										.getString(FluShotEligibility));
								c.setTdapEligibility(o
										.getString(TdapEligibility));

								break;
							}

							if (c != null) {
								final Dialog dialog = new Dialog(
										ClinicScheduleActivity.this,
										android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
								dialog.setContentView(R.layout.clinic_information_layout);
								final TextView txt = (TextView) dialog
										.findViewById(R.id.clinictext);
								// txt.setText("XYZ Clinic(05/18/2015) 123 Main Street, Dallas TX 75231");
								Button yes = (Button) dialog
										.findViewById(R.id.yes);
								yes.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										dialog.dismiss();

									}
								});

								final ImageView closeButton = (ImageView) dialog
										.findViewById(R.id.closeDialog);
								closeButton
										.setOnClickListener(new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												dialog.dismiss();

											}
										});

								// clinic_name_edit_box
								EditText editTxt = (EditText) dialog
										.findViewById(R.id.clinic_name_edit_box);
								editTxt.setText(c.getName());

								// mailing_address_1_edit_box
								editTxt = (EditText) dialog
										.findViewById(R.id.mailing_address_1_edit_box);
								editTxt.setText(c.getAddress());

								// city_edit_box
								editTxt = (EditText) dialog
										.findViewById(R.id.city_edit_box);
								editTxt.setText(c.getCity());

								// state_edit_box
								editTxt = (EditText) dialog
										.findViewById(R.id.state_edit_box);
								editTxt.setText(c.getState());

								// zip_code_edit_box
								editTxt = (EditText) dialog
										.findViewById(R.id.zip_code_edit_box);
								editTxt.setText(c.getZip());

								// main_phone_edit_box
								// editTxt = (EditText)
								// dialog.findViewById(R.id.main_phone_edit_box);

								dialog.show();
								dialog.getWindow().setLayout(1000, 650);
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
						// Hide Progress Dialog
						// prgDialog.hide();
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

	public void fillPatientsTable(List<Patient> patientList) {
		TableRow row;
		TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14;
		ImageView i3, i4, i5, i6, i7, i15;
		Button b1;
		// Converting to dip unit
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 1, getResources().getDisplayMetrics());

		if (patientList == null)
			return;
		int size = patientList.size();

		final TableRow headerRow = new TableRow(this);
		t1 = new TextView(this);
		t1.setText("APPT");
		t1.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t1.setTextSize(textFontSize);
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		t1.setPadding(20 * dip, 0, 0, 0);

		t2 = new TextView(this);
		t2.setText("TYPE");
		t2.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t2.setTextSize(textFontSize);
		t2.setGravity(Gravity.CENTER_HORIZONTAL);
		t2.setPadding(20 * dip, 0, 0, 0);

		t3 = new TextView(this);
		t3.setText("LAST NAME");
		t3.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t3.setTextSize(textFontSize);
		t3.setGravity(Gravity.CENTER_HORIZONTAL);
		t3.setPadding(15 * dip, 0, 0, 0);

		t4 = new TextView(this);
		t4.setText("FIRST NAME");
		t4.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t4.setTextSize(textFontSize);
		t4.setGravity(Gravity.CENTER_HORIZONTAL);
		t4.setPadding(40 * dip, 0, 0, 0);

		t5 = new TextView(this);
		t5.setText("NP");
		t5.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t5.setTextSize(textFontSize);
		t5.setGravity(Gravity.CENTER_HORIZONTAL);
		t5.setPadding(60 * dip, 0, 0, 0);

		t6 = new TextView(this);
		t6.setText("LANG");
		t6.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t6.setTextSize(textFontSize);
		t6.setGravity(Gravity.CENTER_HORIZONTAL);
		t6.setPadding(30 * dip, 0, 0, 0);

		t7 = new TextView(this);
		t7.setText("HQ");
		t7.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t7.setTextSize(textFontSize);
		t7.setGravity(Gravity.CENTER_HORIZONTAL);
		t7.setPadding(20 * dip, 0, 0, 0);

		t8 = new TextView(this);
		t8.setText("HE");
		t8.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t8.setTextSize(textFontSize);
		t8.setGravity(Gravity.CENTER_HORIZONTAL);
		t8.setPadding(30 * dip, 0, 0, 0);

		t9 = new TextView(this);
		t9.setText("NP");
		t9.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t9.setTextSize(textFontSize);
		t9.setGravity(Gravity.CENTER_HORIZONTAL);
		t9.setPadding(30 * dip, 0, 0, 0);

		t10 = new TextView(this);
		t10.setText("FLU");
		t10.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t10.setTextSize(textFontSize);
		t10.setGravity(Gravity.CENTER_HORIZONTAL);
		t10.setPadding(30 * dip, 0, 0, 0);

		t11 = new TextView(this);
		t11.setText("TDAP");
		t11.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t11.setTextSize(textFontSize);
		t11.setGravity(Gravity.CENTER_HORIZONTAL);
		t11.setPadding(30 * dip, 0, 0, 0);

		t12 = new TextView(this);
		t12.setText("HT");
		t12.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t12.setTextSize(textFontSize);
		t12.setGravity(Gravity.CENTER_HORIZONTAL);
		t12.setPadding(60 * dip, 0, 0, 0);

		t13 = new TextView(this);
		t13.setText("ACTION");
		t13.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t13.setTextSize(textFontSize);
		t13.setGravity(Gravity.CENTER_HORIZONTAL);
		t13.setPadding(15 * dip, 0, 0, 0);

		t14 = new TextView(this);
		t14.setText("ELAPSED TIME");
		t14.setTypeface(Typeface.SERIF, Typeface.BOLD);
		t14.setTextSize(textFontSize);
		t14.setGravity(Gravity.CENTER_HORIZONTAL);
		t14.setPadding(30 * dip, 0, 0, 0);

		headerRow.addView(t1);
		headerRow.addView(t2);
		headerRow.addView(t3);
		headerRow.addView(t4);
		headerRow.addView(t5);
		headerRow.addView(t12);
		headerRow.addView(t6);
		headerRow.addView(t7);
		headerRow.addView(t8);
		headerRow.addView(t9);
		headerRow.addView(t10);
		headerRow.addView(t11);

		headerRow.addView(t13);
		headerRow.addView(t14);

		// patients_header_row.addView(headerRow);
		patients_header_row.addView(headerRow, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		int count = 0;

		for (Patient p : patientList) {
			count++;
			row = new TableRow(this);
			final String dob = p.getDOB();
			final String patientId = p.getPatientId();
			final String patientName = p.getFirstName() + " " + p.getLastName();
			t1 = new TextView(this);
			// t1.setTextColor(getResources().getColor(R.color.blue));

			// t1.setText(Patients.abbreviations[current]);
			t1.setText(p.getStartTime());
			// t1.setTypeface(null, 1);
			// t2.setTypeface(null, 1);

			t1.setTextSize(textFontSize);
			t1.setPadding(20 * dip, 0, 0, 0);
			t1.setClickable(true);
			t1.setGravity(Gravity.CENTER_HORIZONTAL);

			t1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					v.setBackgroundColor(Color.GRAY);
					System.out.println("Cell clicked: " + v.getId());

					// get the data you need
					TextView tableCell = (TextView) v;
					String result = tableCell.getText().toString();
					// tableCell.setTextColor(Color.WHITE);
					tableCell.setBackgroundColor(Color.GREEN);
					tableCell.setTypeface(Typeface.SERIF, Typeface.BOLD);
					System.out.println(result);
				}
			});

			// t2.setText(Patients.patients[current]);
			t2 = new TextView(this);
			// t2.setTextColor(getResources().getColor(R.color.green));
			t2.setText("A");
			t2.setTextSize(textFontSize);
			t2.setPadding(30 * dip, 0, 0, 0);
			t2.setGravity(Gravity.CENTER_HORIZONTAL);

			t3 = new TextView(this);
			t3.setId(Integer.parseInt(p.getPatientId()));
			t3.setText(p.getLastName());
			t3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					v.setBackgroundColor(Color.GRAY);
					System.out.println("Cell clicked: " + v.getId());

					// get the data you need
					TextView tableCell = (TextView) v;
					String result = tableCell.getText().toString();
					tableCell.setTextColor(Color.WHITE);
					tableCell.setBackgroundColor(Color.RED);
					tableCell.setTypeface(Typeface.SERIF, Typeface.BOLD);
					System.out
							.println("Launching Edit Patient Details Activity");
					Intent homeIntent = new Intent(getApplicationContext(),
							PatientDetailInfoActivity.class);
					homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					homeIntent.putExtra("authToken", authToken);
					homeIntent.putExtra("patientId", v.getId());
					homeIntent.putExtra("patientName", patientName);
					homeIntent.putExtra("dob", dob);
					startActivity(homeIntent);

				}
			});
			t3.setTextSize(textFontSize);
			t3.setWidth(160);
			t3.setPadding(30 * dip, 0, 0, 0);
			t3.setGravity(Gravity.CENTER_HORIZONTAL);

			t4 = new TextView(this);
			t4.setText(p.getFirstName());
			t4.setTextSize(textFontSize);
			t4.setPadding(30 * dip, 0, 0, 0);
			t4.setWidth(160);
			t4.setGravity(Gravity.CENTER_HORIZONTAL);

			// NP name here
			t5 = new TextView(this);
			t5.setText("Joanne");
			t5.setTextSize(textFontSize);
			t5.setPadding(30 * dip, 0, 0, 0);
			t5.setGravity(Gravity.CENTER_HORIZONTAL);

			// t1.setWidth(LayoutParams.WRAP_CONTENT);
			// t2.setWidth(LayoutParams.WRAP_CONTENT);
			// t1.setPadding(1*dip, 0, 0, 0);
			row.addView(t1);
			row.addView(t2);
			row.addView(t3);
			row.addView(t4);
			row.addView(t5);

			t7 = new TextView(this);
			t7.setText("Sally");
			t7.setTextSize(textFontSize);
			t7.setPadding(40 * dip, 0, 0, 0);
			t7.setGravity(Gravity.CENTER_HORIZONTAL);
			row.addView(t7);

			t6 = new TextView(this);
			t6.setText("E");
			t6.setTextSize(textFontSize);
			t6.setPadding(40 * dip, 0, 0, 0);
			t6.setGravity(Gravity.CENTER_HORIZONTAL);
			row.addView(t6);

			System.out.println("HealthEvalStatus ID --> "
					+ p.getHealthEvalStatusId());

			i3 = new ImageView(this);
			if (count % 2 == 0)
				i3.setImageDrawable(getResources().getDrawable(
						R.drawable.complete_with_overtime));
			else
				i3.setImageDrawable(getResources().getDrawable(
						R.drawable.not_started));
			i3.setPadding(40 * dip, 0, 0, 0);
			row.addView(i3);

			i4 = new ImageView(this);
			i4.setImageDrawable(getResources().getDrawable(R.drawable.overtime));
			i4.setPadding(32 * dip, 0, 0, 0);
			row.addView(i4);

			i5 = new ImageView(this);
			if (count % 2 == 0)
				i5.setImageDrawable(getResources().getDrawable(
						R.drawable.not_started));
			else
				i5.setImageDrawable(getResources().getDrawable(
						R.drawable.overtime));
			i5.setPadding(25 * dip, 0, 0, 0);
			row.addView(i5);

			i6 = new ImageView(this);
			if (count % 2 == 0)
				i6.setImageDrawable(getResources().getDrawable(
						R.drawable.ineligible));
			else
				i6.setImageDrawable(getResources().getDrawable(
						R.drawable.administered));
			i6.setPadding(25 * dip, 0, 0, 0);
			row.addView(i6);

			i7 = new ImageView(this);
			i7.setImageDrawable(getResources().getDrawable(R.drawable.eligible));
			i7.setPadding(40 * dip, 0, 0, 0);
			row.addView(i7);

			row.setPadding(0, 10, 0, 10);

			/*
			 * t8 = new TextView(this); t8.setText("Start HE");
			 * t8.setTextSize(textFontSize); t8.setPadding(20*dip, 0, 0, 0);
			 * t8.setGravity(Gravity.CENTER_HORIZONTAL); row.addView(t8);
			 */

			i15 = new ImageView(this);
			i15.setImageDrawable(getResources().getDrawable(R.drawable.action));
			i15.setPadding(50 * dip, 0, 0, 0);
			row.addView(i15);
			i15.getLayoutParams().height = 30;
			i15.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					System.out.println("Cell clicked: " + v.getId());

					// get the data you need
					ImageView tableCell = (ImageView) v;
					// tableCell.setTextColor(Color.WHITE);
					System.out
							.println("Launching Edit Patient Verification Activity");
					Intent homeIntent = new Intent(getApplicationContext(),
							PatientVerificationActivity.class);
					homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					homeIntent.putExtra("authToken", authToken);
					System.out.println("OnClickListener set DOB = " + dob);
					homeIntent.putExtra("dateOfBirth", dob);
					homeIntent.putExtra("patientId", patientId);
					homeIntent.putExtra("patientName", patientId);
					startActivity(homeIntent);
				}
			});

			/*
			 * b1 = new Button(this); b1.setText("Start Px Workflow");
			 * b1.setTextSize(14); //
			 * b1.setBackgroundColor(getResources().getColor(R.color.orange));
			 * b1.setPadding(20 * dip, 0, 0, 0); ; b1.setWidth(100);
			 * b1.setGravity(Gravity.VERTICAL_GRAVITY_MASK); row.addView(b1);
			 * b1.setClickable(true); b1.setShadowLayer(5f, -1, 1,
			 * Color.LTGRAY);
			 */

			/*
			 * i15 = new ImageView(this);
			 * i15.setImageDrawable(getResources().getDrawable(
			 * R.drawable.exclamation)); i15.setPadding(20 * dip, 0, 0, 0);
			 * row.addView(i15); i15.getLayoutParams().height = 30;
			 */
			/*
			 * android:layout_width="match_parent" android:layout_height="30dp"
			 */
			t14 = new TextView(this);
			t14.setText(p.getElapsedTime());
			t14.setTextSize(textFontSize);
			t14.setPadding(90 * dip, 0, 0, 0);
			t14.setGravity(Gravity.CENTER_HORIZONTAL);
			row.addView(t14);

			row.setBackground(getResources().getDrawable(R.drawable.shape));
			row.setId(Integer.parseInt(p.getPatientId()));
			;

			patients_table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		}

	}

	public void showActivity() {

		mylist = new ArrayList<HashMap<String, String>>();
		mylist_title = new ArrayList<HashMap<String, String>>();
		map1 = new HashMap<String, String>();
		map1.put("appt", "APPT");
		map1.put("type", "TYPE");
		map1.put("lastname", " LAST NAME");
		map1.put("firstname", "FIRST NAME");
		map1.put("patientid", "PATIENT ID");
		map1.put("lang", "LANG");
		map1.put("hq", "HQ");
		map1.put("he", "HE");
		map1.put("np", "NP");
		map1.put("flushot", "FLUS");
		map1.put("tdap", "TDAP");
		map1.put("team", "TEAM");
		map1.put("action", "ACTION");
		map1.put("alerts", "ALERTS");
		mylist_title.add(map1);

		try {
			adapter_title = new SimpleAdapter(this, mylist_title,
					R.layout.header_row, new String[] { "appt", "type",
							"lastname", "firstname", "patientid", "lang", "hq",
							"he", "np", "flushot", "tdap", "team", "action",
							"alerts" }, new int[] { R.id.Appt, R.id.Type,
							R.id.Lastname, R.id.Firstname, R.id.Patientid,
							R.id.Language, R.id.HQ, R.id.HE, R.id.NP,
							R.id.FLUSHOT, R.id.TDAP, R.id.Team, R.id.Action,
							R.id.Alerts });
			list_head.setAdapter(adapter_title);
		} catch (Exception e) {

		}

		/*
		 * for (int i = 0; i < Appointment.length; i++) { map2 = new
		 * HashMap<String, String>(); map2.put("appt", Appointment[i]);
		 * map2.put("type", type[i]); map2.put("lastname", Lastname[i]);
		 * map2.put("firstname",Firstname[i]); map2.put("patientid",
		 * PatientId[i]); map2.put("lang", Language[i]);
		 * map2.put("hq",String.valueOf(HQ[i]));
		 * map2.put("he",String.valueOf(HE[i]));
		 * map2.put("np",String.valueOf(NP[i]));
		 * map2.put("flushot",String.valueOf(FLUSHOT[i]));
		 * map2.put("tdap",String.valueOf(TDAP[i])); map2.put("team",Team[i]);
		 * map2.put("action",Action[i]);
		 * map2.put("alerts",String.valueOf(Alerts[i])); mylist.add(map2); }
		 * 
		 * try { adapter = new SimpleAdapter(this, mylist, R.layout.row, new
		 * String[] { "appt", "type",
		 * "lastname","firstname","patientid","lang","hq"
		 * ,"he","np","flushot","tdap","team","action","alerts"}, new int[] {
		 * R.id.Appt, R.id.Type, R.id.Lastname, R.id.Firstname,
		 * R.id.Patientid,R.
		 * id.Language,R.id.HQ,R.id.HE,R.id.NP,R.id.FLUSHOT,R.id
		 * .TDAP,R.id.Team,R.id.Action,R.id.Alerts}); list.setAdapter(adapter);
		 * } catch (Exception e) {
		 * 
		 * }
		 */

	}

	private List<Patient> getPatientList(String authToken) {
		// prgDialog = new ProgressDialog(this);
		// Set Progress Dialog Text
		// prgDialog.setMessage("Please wait...");
		// prgDialog.setCancelable(false);
		// Show Progress Dialog
		// prgDialog.show();

		String restAPIUrl = "http://10.100.100.35/CatapultStaging/gateway/api/v1/api-gateway.aspx";

		AsyncHttpClient client = new AsyncHttpClient();

		HttpEntity entity = null;
		final List<Patient> patients = new ArrayList<Patient>();

		try {

			String payload = "{\"type\":\"dbo-request\",\"dboName\":\"FetchPatientsByClinicId\",\"clinicId\":\"a0CC000000T3pfMMAR\", \"authToken\":\""
					+ authToken + "\"}";

			payload = URLEncoder.encode(payload, "utf-8");
			entity = new StringEntity("package=" + payload, "UTF-8");
			System.out.println("payload = " + payload);

		} catch (IllegalArgumentException e) {
			System.err.println("HTTP StringEntity: IllegalArgumentException");
			return null;
		} catch (UnsupportedEncodingException e) {
			System.err
					.println("HTTP StringEntity: UnsupportedEncodingException");
			return null;
		}

		System.out
				.println("About to make a POST call to retrieve Patient List for authToken ="
						+ authToken);

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
						// prgDialog.hide();
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

							// showActivity();

							if (patients != null)
								fillPatientsTable(patients);

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
						// Hide Progress Dialog
						// prgDialog.hide();
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

		// TODO delete
		// Hide Progress Dialog

		// prgDialog.hide();

		return patients;
	}

	public void serve(View v) {
		Intent homeIntent;
		/*
		 * switch(v.getId()) { case R.id.serve: // R.id.textView1 intent = new
		 * Intent(this, Second.class); break; case R.id.complain: //
		 * R.id.textView2 intent = new Intent(this, Third.class); break; case
		 * R.id.feed: // R.id.textView3 intent = new Intent(); }
		 */

		homeIntent = new Intent(this, TeamLeadHome.class);
		homeIntent.putExtra("authToken", authToken);

		startActivity(homeIntent);
	}
}
