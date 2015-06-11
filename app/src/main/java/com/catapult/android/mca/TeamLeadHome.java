package com.catapult.android.mca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamLeadHome extends Activity {
	 ImageView clinicSchedule;
	    ImageView clinicInfo;
	    ImageView dailyStats;
	    ImageView staff;
	    ImageView closeOut;
	    TextView mNameData;
	    TextView mAddressData;
	    TextView mContactData;
	    TextView mPhoneNumberData;
	    TextView mClinicType;
	    
	    String authToken = null;
		String dob, patId, patName = null;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_lead_home);
		
		clinicSchedule = (ImageView)findViewById(R.id.tl_clinic_schedule);
        clinicInfo = (ImageView)findViewById(R.id.tl_clinic_info);
        dailyStats = (ImageView)findViewById(R.id.tl_daily_stats);
        staff = (ImageView)findViewById(R.id.tl_staff);
        closeOut = (ImageView)findViewById(R.id.tl_closeout);
        
        Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");

        clinicSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamLeadHome.this, ClinicScheduleActivity.class);
                setIntentAttributes(intent);
                startActivity(intent);
            }
        });
        
        clinicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamLeadHome.this, HT_Manual_Piccolo_Activity.class);
                setIntentAttributes(intent);
                startActivity(intent);
            }
        });
        
        dailyStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamLeadHome.this, HT_Manual_Piccolo_Activity.class);
                setIntentAttributes(intent);
                startActivity(intent);
            }
        });
        
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamLeadHome.this, TL_Staff_Activity.class);
                setIntentAttributes(intent);
                startActivity(intent);
            }
        });
        
        closeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamLeadHome.this, HT_Manual_Piccolo_Activity.class);
                setIntentAttributes(intent);
                startActivity(intent);
            }
        });
        
        
	}
	
	public void setIntentAttributes(Intent homeIntent) {
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
	}
}
