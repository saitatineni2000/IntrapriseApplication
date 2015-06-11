package com.catapult.android.mca;

import com.catapult.android.mca.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PatientDetailsActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_patient_details);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);
		TableLayout mainTable = (TableLayout) findViewById(R.id.tablelayout1);
		//mainTable.setBackground(getResources().getDrawable(R.drawable.shape));
		 for (int i=0; i < 5; i ++) {
		        TableRow tr=new TableRow(this);
		        tr.setId(1000 + i);
		        tr.setClickable(true);  
		        
		        tr.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		                v.setBackgroundColor(Color.GRAY);
		                System.out.println("Row clicked: " + v.getId());

		               //get the data you need
		               TableRow tablerow = (TableRow)v;
		               TextView sample = (TextView) tablerow.getChildAt(2);
		               String result=sample.getText().toString();
		               System.out.println(tablerow.getId());
		               
		            }
		        });
		        /*tr.setOnTouchListener(new OnTouchListener() {
		            public boolean onTouch(View v, MotionEvent m) {
		                v.setBackgroundColor(Color.GRAY);
		                System.out.println("Row clicked: " + v.getId());

		               //get the data you need
		               TableRow tablerow = (TableRow)v.getParent();
		               TextView sample = (TextView) tablerow.getChildAt(2);
		               String result=sample.getText().toString();
		               System.out.println(tablerow.getId());
		                return true;
		            }
		        });*/
		        // 
		        //tr.setBackground(getResources().getDrawable(R.drawable.shape));
		        
		        TextView labelName=new TextView(this);
		        labelName.setId(2000 + i);
		        labelName.setText("Test Col 11111111111111");
		        labelName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
		        labelName.setBackground(getResources().getDrawable(R.drawable.shape));
		        
		        tr.addView(labelName);

		        /*TextView labelBarcode=new TextView(this);
		        labelBarcode.setId(3000+i);
		        labelBarcode.setText("Test Col 2");
		        LayoutParams l = new LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		        labelBarcode.setLayoutParams(new TableRow.LayoutParams(l));*/
		        
		        /*final LineDrawable d = new LineDrawable();
		        d.setLevel(4000);
		        labelBarcode.setBackground(d);*/
		       
		        /* ShapeDrawable border = new ShapeDrawable(new RectShape());
		        border.getPaint().setStyle(Style.STROKE);
		        
		        border.getPaint().setColor(Color.BLACK);
		        labelBarcode.setBackground(border);*/
		        
		        /*labelBarcode.setBackground(getResources().getDrawable(R.drawable.shape));
		        tr.addView(labelBarcode);*/
		        TextView labelBarcode=new TextView(this);
		        labelBarcode.setId(2000 + i);
		        labelBarcode.setText("Test Col 222");
		        labelBarcode.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
		        labelBarcode.setBackground(getResources().getDrawable(R.drawable.shape));
		        tr.addView(labelBarcode);
		        
		        
		        // Column #2
		        TextView labelCost=new TextView(this);
		        labelCost.setId(4000+i);
		        labelCost.setText("Test  Col333333333333333333333");
		        labelCost.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
		        labelCost.setBackground(getResources().getDrawable(R.drawable.shape));
		        tr.addView(labelCost);
		        
		        
		        mainTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));

		    }

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
