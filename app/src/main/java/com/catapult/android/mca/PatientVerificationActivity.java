package com.catapult.android.mca;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PatientVerificationActivity extends FragmentActivity  {
	
	/*private CalendarView calendarView;
    private int yr, mon, dy;
    private Calendar selectedDate;*/
    private int year;
	private int month;
	private int day;
	private DatePicker datePicker;
	EditText calEditText;
	static final int DATE_DIALOG_ID = 1;
	String authToken = null;
	String dob = null, patId = null, patName = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_verification);
		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");
		
		ImageButton calImageBtn = (ImageButton) findViewById(R.id.pv_calendar_image_button);
		calEditText = (EditText) findViewById(R.id.DateofbirthID);
		datePicker = (DatePicker) findViewById(R.id.pv_datePicker);
		calImageBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				onCreateDialog(DATE_DIALOG_ID).show();
			}
		});

		final Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
    }

    /*private DatePickerDialog.OnDateSetListener dateListener =
        new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int
            monthOfYear, int dayOfMonth){
            selectedDate=Calendar.getInstance();
            yr=year;
            mon=monthOfYear;
            dy=dayOfMonth;
            selectedDate.set(yr, mon, dy);
           calendarView.setDate(selectedDate.getTimeInMillis());
        }
    };*/
    
    private void updateDisplay() {
		
    	String monthValue = month > 9? (month+1)+"" :  "0" + (month+1);
    	String dayValue = day > 9? day+"" :  "0" + day;
    	calEditText.setText(new StringBuilder().append(monthValue).append("/")
				.append(dayValue).append("/").append(year).append(" "));
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

    /**
     * 
     * @param view
     */
    public void Submit(View view) {
		System.out.println("Inside PatientVerificationActivity:Submit");
		Intent homeIntent = new Intent(getApplicationContext(),
				PiccoloSetupActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);
		
		EditText datePickerText = (EditText) findViewById(
	            R.id.DateofbirthID);
		System.out.println("HT Entered DOB = "+datePickerText.getText().toString().trim() + ", length = "+datePickerText.getText().toString().trim().length());
		System.out.println("Incoming Patient DOB = "+dob.trim() + ", length ="+dob.trim().length());
		
		if (datePickerText.getText().toString().trim().equals(dob.trim())) {
			System.out.println("We have a match!");
			startActivity(homeIntent);
		}
		else {
			System.out.println("OOPS!");
			final Dialog dialog = new Dialog(PatientVerificationActivity.this);
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Patient Verification Alert");
            TextView sampleText = (TextView) dialog.findViewById(R.id.dialog_text);
            sampleText.setText("Invalid Patient birth date.  Please try again");
            
            Button okBtn = (Button) dialog.findViewById(R.id.btn_ok);
            dialog.show();
	        okBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dialog.dismiss();
		        };
	        });
		}
	}
    
    public void Cancel(View view) {
		System.out.println("Inside PatientVerificationActivity:Close");
		Intent homeIntent = new Intent(getApplicationContext(),
				ClinicScheduleActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		startActivity(homeIntent);
			
	}
    

	/*public static void showPinValidationDialog(final Activity activity) {
		currentActivity = activity;
		
		final Dialog dialog = new Dialog(activity);
		dialog.setContentView(R.layout.validate_pin);
		dialog.setTitle(activity
				.getString(R.string.prefs_parental_pin_validate_title));
		dialog.setCancelable(false);

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(activity.getString(R.string.prefs_pin_validate_text) + ":");

		final EditText pinText = (EditText) dialog.findViewById(R.id.pinText);
		pinText.addTextChangedListener(pinTextWatcher);
		pinText.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputMethodManager keyboard = (InputMethodManager) currentActivity
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				keyboard.showSoftInput(pinText, 0);
			}
		}, 50);

		dialogButtonValidate = (Button) dialog.findViewById(R.id.btnOK);
		dialogButtonValidate.setEnabled(false);
		dialogButtonValidate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strPin = "";
				strPin = pinText.getText().toString();
				//Log.i(ROSApplication.LOG_PUSH_TAG, "dialogButton ok");
				if (strPin != null && !strPin.equalsIgnoreCase(""))
					validatePin(activity, dialog, strPin);

			}
		});
		
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(ROSApplication.LOG_PUSH_TAG, "dialogButton cancel");
				dialog.dismiss();
			}
		});

		dialog.setOnKeyListener(onKeyListener);
		dialog.show();
	}*/

}




































