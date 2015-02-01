package com.parash.expensechecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditExpenseActivity extends Activity {
	
	private EditText et_title;
	private EditText et_dd;
	private EditText et_mm;
	private EditText et_yyyy;
	private EditText et_cost;
	private EditText et_currency;
	private Button b_save;
	
	private Expense recieved;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		et_title = (EditText) findViewById( R.id.et_expense_title );
		et_dd = (EditText) findViewById( R.id.et_expensedd );
		et_mm = (EditText) findViewById( R.id.et_expensemm );
		et_yyyy = (EditText) findViewById( R.id.et_expenseyyyy );
		et_cost = (EditText) findViewById( R.id.et_expenseCost );
		et_currency = (EditText) findViewById( R.id.et_expenseCurrency );
		
		b_save = (Button) findViewById( R.id.b_saveExpense );
		
		b_save.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), EditExpenseActivity.class);
				
				i.putExtra("respectiveExpense", recieved);
				
				i.putExtra("indexOfExpense", getIntent().getIntExtra("indexOfExpense", 0));
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		recieved = (Expense) getIntent().getSerializableExtra("respectiveExpense");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
}
