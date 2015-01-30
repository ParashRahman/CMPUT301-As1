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
	private Claim recieved;
	
	private EditText et_title;
	private EditText et_dd;
	private EditText et_mm;
	private EditText et_yyyy;
	private EditText et_todd;
	private EditText et_tomm;
	private EditText et_toyyyy;
	private EditText et_cost;
	private EditText et_cur;
	private CheckBox ranged;
	
	private static int[] toHide = { R.id.range1, R.id.range2, R.id.range3, R.id.range4, R.id.range5, R.id.range6 };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.add_claim);        
        
        saveButton = (Button) findViewById( R.id.saveClaim );
        ranged = (CheckBox) findViewById(R.id.cb_ranged);
        
        ranged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        	// OnCheckedChangeListener brought to you by http://stackoverflow.com/questions/8386832/android-checkbox-listener
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
            	hideRangedDate();
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
		
		
	}
	
	
	
}
