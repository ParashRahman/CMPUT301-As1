package com.parash.expensechecker;

/** 
 * 
 * The MIT License (MIT)

Copyright (c) 2015 ParashRahman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


 * 
 * **/

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class EditClaimActivity extends Activity{
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
        
		
        ranged = (CheckBox) findViewById(R.id.cb_ranged);
        
        et_title = (EditText) findViewById(R.id.et_claim_title);
        et_dd = (EditText) findViewById(R.id.et_claim_dd);
        et_mm = (EditText) findViewById(R.id.et_claim_mm);
        et_yyyy = (EditText) findViewById(R.id.et_claim_yyyy);
        et_todd = (EditText) findViewById(R.id.et_range2);
        et_tomm = (EditText) findViewById(R.id.et_range4);
        et_toyyyy = (EditText) findViewById(R.id.et_range6);
        
        saveButton = (Button) findViewById( R.id.saveClaim );
        
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
				plugItIn();	
			}
        }); 
       
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

        
        hideRangedDate();
		
        recieved = (Claim) getIntent().getSerializableExtra("respectiveClaim");

		plugItOut();
	}



	private boolean hideRangedDate(){
		if ( ! ranged.isChecked() ){
			for ( int i = 0; i < toHide.length; i++ ){
				View h = findViewById(toHide[i]);
				h.setVisibility(View.INVISIBLE);
			}
			return false;
		} else {
			for ( int i = 0; i < toHide.length; i++ ){
				View h = findViewById(toHide[i]);
				h.setVisibility(View.VISIBLE);
			}
			return true;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		finish();
		return;
	}

	private void plugItIn() {
		String name = et_title.getText().toString();
		
		GregorianCalendar fdate = null;
		GregorianCalendar tdate = null;
				
		String day = et_dd.getText().toString();
		String month = et_mm.getText().toString();
		String year = et_yyyy.getText().toString();
				
		if ( Helpers.isIntegerParsable(day) && Helpers.isIntegerParsable(month) && Helpers.isIntegerParsable(year) ){
			fdate = new GregorianCalendar();
			fdate.set( Calendar.DAY_OF_MONTH, Integer.parseInt(day) );
			fdate.set( Calendar.MONTH, Integer.parseInt(month) - 1 );
			fdate.set( Calendar.YEAR, Integer.parseInt(year) );
		}
		
		if ( ranged.isChecked() ){		
			day = et_todd.getText().toString();
			month = et_tomm.getText().toString();
			year = et_toyyyy.getText().toString();
			
			if ( Helpers.isIntegerParsable(day) && Helpers.isIntegerParsable(month) && Helpers.isIntegerParsable(year)){
				tdate = new GregorianCalendar();
				
				tdate.set( Calendar.DAY_OF_MONTH, Integer.parseInt(day));
				tdate.set( Calendar.MONTH, Integer.parseInt(month) - 1 );
				tdate.set( Calendar.YEAR, Integer.parseInt(year) );
			}
		} 
				
		recieved.setName( name );		
		recieved.setFromDate( fdate );
		recieved.setToDate( tdate );
				
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		
		int indexToReturn = getIntent().getIntExtra("indexOfClaim", 0);
		i.putExtra("respectiveClaim", recieved);
		i.putExtra("indexOfClaim", indexToReturn);
				
		startActivity(i);
	}
	
	private void plugItOut(){
		et_title.setText(recieved.getName());
		if ( recieved.getFromDate() != null ){
			et_dd.setText( Integer.toString( recieved.getFromDate().get(Calendar.DAY_OF_MONTH) ) );
			et_mm.setText( Integer.toString( recieved.getFromDate().get(Calendar.MONTH) + 1) );
			et_yyyy.setText( Integer.toString( recieved.getFromDate().get(Calendar.YEAR) ) );
		}
		
		if ( recieved.getToDate() != null ){
			ranged.setChecked(true);
			et_todd.setText( Integer.toString( recieved.getToDate().get(Calendar.DAY_OF_MONTH) ) );
			et_tomm.setText( Integer.toString( recieved.getToDate().get(Calendar.MONTH) + 1 ) );
			et_toyyyy.setText( Integer.toString( recieved.getToDate().get(Calendar.YEAR) ) );
		} else {
			ranged.setChecked( false );
		}
		
	}
}
