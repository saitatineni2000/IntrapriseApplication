package com.catapult.android.mca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TL_Staff_Activity extends Activity {

	TableLayout staffTable;
	TextView staffLastName;
	TextView staffFirstName;
	TextView staffID;
	TextView staffRole;
	TextView staffStartTime;
	TextView staffOutTime;
	TextView staffPhone;
	TextView staffEmail;
	TextView staffStatus;

	TableRow tablerow;

	String[] sFirstName = { "Smith", "Williamson", "Johnson", "Smith",
			"Thompson", "Jackson", "Gomez", "Gomez" };
	String[] sLastName = { "Mary", "Elizabeth", "Steve", "James", "Henry",
			"Louis", "Ricardo", "Juliana" };
	int[] sID = { 65742453, 12232354, 35678780, 12343456, 12334545, 12323487,
			12343509, 12343897 };
	String[] sRole = { "Health Tech", "Health Tech", "Health Tech", "NP", "NP",
			"NP", "Health Tech", "NP" };
	String[] sStartTime = { "8:30", "8:30", "8:30", "8:30", "8:30", "9:30",
			"9:30", "11:00" };
	String[] sOutTime = { "", "", "", "", "", "", "", "" };
	String[] sPhone = { "208-675-9876", "208-765-2345", "208-123-2345",
			"208-012-0098", "208-786-8765", "208-098-1676", "208-564-8876",
			"208-990-2231" };
	String[] sEmail = { "mary.smith@catapulthealth.com",
			"elizabeth.williamson@catapulthealth.com",
			"steve.johnson@catapulthealth.com",
			"james.smith@catapulthealth.com",
			"henry.thompson@catapulthealth.com",
			"louis.jackson@catapulthealth.com",
			"ricardo.gomez@catapulthealth.com",
			"juliana.gomez@catapulthealth.com" };
	String[] sStatus = { "Break", "Break", "On", "Break", "On", "On", "On",
			"On" };

	String authToken = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tl_staff);
		staffTable = (TableLayout) findViewById(R.id.staff_header_row);
		populateRows();
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");

	}

	public void populateRows() {

		for (int i = 0; i < sLastName.length; i++) {
			tablerow = new TableRow(this);
			tablerow.setLayoutParams(new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.WRAP_CONTENT));
			// tablerow.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.setPadding(10, 0, 10, 0);
			tablerow.setWeightSum(10);

			staffFirstName = new TextView(this);
			staffLastName = new TextView(this);
			staffID = new TextView(this);
			staffRole = new TextView(this);
			staffStartTime = new TextView(this);
			staffOutTime = new TextView(this);
			staffPhone = new TextView(this);
			staffEmail = new TextView(this);
			staffStatus = new TextView(this);

			staffFirstName.setText(sFirstName[i]);
			staffFirstName.setTextColor(Color.parseColor("#000000"));
			staffFirstName
					.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.addView(staffFirstName);

			staffLastName.setText(sLastName[i]);
			staffLastName.setTextColor(Color.parseColor("#000000"));
			staffLastName
					.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.addView(staffLastName);

			staffID.setText("" + sID[i]);
			staffID.setTextColor(Color.parseColor("#000000"));
			staffID.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.addView(staffID);

			staffRole.setText(sRole[i]);
			staffRole.setTextColor(Color.parseColor("#000000"));
			staffRole.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.addView(staffRole);

			staffStartTime.setText(sStartTime[i]);
			staffStartTime.setTextColor(Color.parseColor("#000000"));
			staffStartTime
					.setBackgroundResource(R.drawable.staff_rectangular_box);
			staffStartTime.setGravity(Gravity.CENTER);
			tablerow.addView(staffStartTime);

			staffOutTime.setText(sOutTime[i]);
			staffOutTime.setTextColor(Color.parseColor("#000000"));
			staffOutTime
					.setBackgroundResource(R.drawable.staff_rectangular_box);
			staffOutTime.setGravity(Gravity.CENTER);
			tablerow.addView(staffOutTime);

			staffPhone.setText(sPhone[i]);
			staffPhone.setTextColor(Color.parseColor("#000000"));
			staffPhone.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.addView(staffPhone);

			staffEmail.setText(sEmail[i]);
			staffEmail.setTextColor(Color.parseColor("#000000"));
			staffEmail.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.addView(staffEmail);

			staffStatus.setText(sStatus[i]);
			staffStatus.setTextColor(Color.parseColor("#000000"));
			staffStatus.setBackgroundResource(R.drawable.staff_rectangular_box);
			tablerow.addView(staffStatus);

			staffTable.addView(tablerow);
		}
	}

	public void serve(View v) {
		Intent homeIntent = new Intent(this, TeamLeadHome.class);
		setIntentAttributes(homeIntent);
		startActivity(homeIntent);
	}

	public void setIntentAttributes(Intent homeIntent) {
		homeIntent.putExtra("authToken", authToken);
	}
}
