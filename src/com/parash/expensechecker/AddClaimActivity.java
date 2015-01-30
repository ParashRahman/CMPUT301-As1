package com.parash.expensechecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class AddClaimActivity extends Activity{
	private Button saveButton;
	private Button submitButton;
	private Claim recieved;
	
	private EditText et_title;
	private EditText et_dd;
	private EditText et_mm;
	private EditText et_yyyy;
	private EditText et_todd;
	private EditText et_tomm;
	private EditText et_toyyyy;
	private CheckBox ranged;
	
	private static int[] toHide = { R.id.range1, R.id.et_range2, R.id.range3, R.id.et_range4, R.id.range5, R.id.et_range6 };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.add_claim);        
        
        saveButton = (Button) findViewById( R.id.saveClaim );
        ranged = (CheckBox) findViewById(R.id.cb_ranged);
        
        et_title = (EditText) findViewById(R.id.et_claim_title);
        et_dd = (EditText) findViewById(R.id.et_claim_dd);
        et_mm = (EditText) findViewById(R.id.et_claim_mm);
        et_yyyy = (EditText) findViewById(R.id.et_claim_yyyy);
        et_todd = (EditText) findViewById(R.id.et_range2);
        et_tomm = (EditText) findViewById(R.id.et_range4);
        et_toyyyy = (EditText) findViewById(R.id.et_range6);
        
        ranged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        	// OnCheckedChangeListener brought to you by http://stackoverflow.com/questions/8386832/android-checkbox-listener
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
            	hideRangedDate();
            }
        });
        
        saveButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				return;
			}
        });
        
        submitButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				recieved.setStatus('P');
			}
        }); 
        
        recieved = (Claim) getIntent().getSerializableExtra("respectiveClaim");
	}
	
	private void hideRangedDate(){
		if ( ! ranged.isChecked() ){
			for ( int i = 0; i < toHide.length; i++ ){
				View h = findViewById(toHide[i]);
				h.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		String name = et_title.getText().toString();
		Date fdate = new Date();
		Date tdate = new Date();
		
		String day = et_dd.getText().toString();
		String month = et_mm.getText().toString();
		String year = et_yyyy.getText().toString();
		
		if ( Helpers.isIntegerParsable(day) ){
			fdate.setDay(Integer.parseInt(day));
		}
		
		if ( Helpers.isIntegerParsable(month) ){
			fdate.setMonth(Integer.parseInt(month) );
		}
		
		if ( Helpers.isIntegerParsable(year) ){
			fdate.setYear( Integer.parseInt(year) );
		}
		
		if ( ranged.isChecked() ){
			day = et_todd.getText().toString();
			month = et_tomm.getText().toString();
			year = et_toyyyy.getText().toString();
			
			if ( Helpers.isIntegerParsable(day) ){
				tdate.setDay(Integer.parseInt(day));
			}
			
			if ( Helpers.isIntegerParsable(month) ){
				tdate.setMonth(Integer.parseInt(month) );
			}
			
			if ( Helpers.isIntegerParsable(year) ){
				tdate.setYear( Integer.parseInt(year) );
			}
		}
		
		recieved.setName( name );
		recieved.setFromDate( fdate );
		recieved.setToDate( tdate );
	}
	
}
