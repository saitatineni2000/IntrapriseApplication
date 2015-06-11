package com.catapult.android.mca;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HT_Vaccine_Pat_Signature_Activity extends Activity {

	String authToken = null;
	String dob, patId, patName = null;
	TextView vaccineText;
	ImageView piccoloSetupImage;
	ImageView a1cImage;
	ImageView manualClinicMetricsImage;
	ImageView vaccineImage;
	TextView mainText;
	TextView confirmationText;
	CheckBox influenzaCheckbox;
	CheckBox tdapCheckbox;
	signature mSignature;
	Paint paint;
	LinearLayout mContent;
	Button clear, save;
	ImageView signImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ht_vaccine_pat_signature);
		View decorView = getWindow().getDecorView();
		// Hide both the navigation bar and the status bar.
		// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and
		// higher, but as
		// a general rule, you should design your app to hide the status bar
		// whenever you
		// hide the navigation bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE;
		decorView.setSystemUiVisibility(uiOptions);

		Bundle extras = getIntent().getExtras();
		authToken = extras.getString("authToken");
		dob = extras.getString("dateOfBirth");
		patId = extras.getString("patientId");
		patName = extras.getString("patientName");

		vaccineText = (TextView) findViewById(R.id.vaccine_title_text);
		piccoloSetupImage = (ImageView) findViewById(R.id.image10);
		a1cImage = (ImageView) findViewById(R.id.image20);
		manualClinicMetricsImage = (ImageView) findViewById(R.id.image30);
		vaccineImage = (ImageView) findViewById(R.id.image40);
		mainText = (TextView) findViewById(R.id.main_text);
		confirmationText = (TextView) findViewById(R.id.confirmation_text);
		influenzaCheckbox = (CheckBox) findViewById(R.id.influenza_check_box);
		tdapCheckbox = (CheckBox) findViewById(R.id.tdap_check_box);
		save = (Button) findViewById(R.id.save);
		save.setEnabled(false);
		clear = (Button) findViewById(R.id.clear);
		mContent = (LinearLayout) findViewById(R.id.mysignature);
		signImage = (ImageView) findViewById(R.id.imageView1);
		mSignature = new signature(this, null);
		mContent.addView(mSignature);

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v == save) {
					mSignature.save();
				}

			}
		});
		clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (v == clear) {
					mSignature.clear();
				}

			}
		});
	}

	public void nextScreen(View view) {
		System.out.println("Inside HT_Vaccine_Yes_No_Activity:Yes");
		Intent homeIntent = new Intent(getApplicationContext(),
				HT_Vaccine_Complete_Activity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		homeIntent.putExtra("authToken", authToken);
		homeIntent.putExtra("patientId", patId);
		homeIntent.putExtra("patientName", patName);
		homeIntent.putExtra("dateOfBirth", dob);

		startActivity(homeIntent);
	}

	public class signature extends View {
		static final float STROKE_WIDTH = 10f;
		static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		Paint paint = new Paint();
		Path path = new Path();

		float lastTouchX;
		float lastTouchY;
		final RectF rect = new RectF();

		public signature(Context context, AttributeSet attrs) {
			super(context, attrs);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(STROKE_WIDTH);
		}

		public void clear() {
			path.reset();
			invalidate();
			save.setEnabled(false);
		}

		public void save() {
			Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),
					mContent.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(returnedBitmap);
			Drawable bgDrawable = mContent.getBackground();
			if (bgDrawable != null)
				bgDrawable.draw(canvas);
			else
				canvas.drawColor(Color.WHITE);
			mContent.draw(canvas);

			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			returnedBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
			Intent intent = new Intent(HT_Vaccine_Pat_Signature_Activity.this,
					HT_Vaccine_Complete_Activity.class);
			byte[] bytes = bs.toByteArray();
			intent.putExtra("byteArray", bytes);
			intent.putExtra("authToken", authToken);
			intent.putExtra("dateOfBirth", dob);
			intent.putExtra("patientId", patId);
			System.out.println(bytes);
			startActivity(intent);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			save.setEnabled(true);

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				path.moveTo(eventX, eventY);
				lastTouchX = eventX;
				lastTouchY = eventY;
				return true;

			case MotionEvent.ACTION_MOVE:

			case MotionEvent.ACTION_UP:

				resetDirtyRect(eventX, eventY);
				int historySize = event.getHistorySize();
				for (int i = 0; i < historySize; i++) {
					float historicalX = event.getHistoricalX(i);
					float historicalY = event.getHistoricalY(i);
					path.lineTo(historicalX, historicalY);
				}
				path.lineTo(eventX, eventY);
				break;
			}

			invalidate((int) (rect.left - HALF_STROKE_WIDTH),
					(int) (rect.top - HALF_STROKE_WIDTH),
					(int) (rect.right + HALF_STROKE_WIDTH),
					(int) (rect.bottom + HALF_STROKE_WIDTH));

			lastTouchX = eventX;
			lastTouchY = eventY;

			return true;
		}

		private void resetDirtyRect(float eventX, float eventY) {
			rect.left = Math.min(lastTouchX, eventX);
			rect.right = Math.max(lastTouchX, eventX);
			rect.top = Math.min(lastTouchY, eventY);
			rect.bottom = Math.max(lastTouchY, eventY);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 1) {
			Bitmap b = BitmapFactory.decodeByteArray(
					data.getByteArrayExtra("byteArray"), 0,
					data.getByteArrayExtra("byteArray").length);
			signImage.setImageBitmap(b);
		}
	}
	
	@Override
	public void onBackPressed() {
		System.out.println("Back pressed");
	}

}
