package com.parash.expensechecker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
		
        setContentView(R.layout.add_expense);
		
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
				plugItIn();
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		recieved = (Expense) getIntent().getSerializableExtra("respectiveExpense");
		plugItOut();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	private void plugItIn(){
		String title = et_title.getText().toString();
		String curr = et_currency.getText().toString();
		
		Double cost = null;
		String c = et_cost.getText().toString();
		if ( Helpers.isDoubleParsable(c) ){
			cost = Double.parseDouble(c);
		}
		
		String day = et_dd.getText().toString();
		String month = et_mm.getText().toString();
		String year = et_yyyy.getText().toString();
		
		GregorianCalendar date = null;
		if ( Helpers.isIntegerParsable(day) && Helpers.isIntegerParsable(month) && Helpers.isIntegerParsable(year) ){
			date = new GregorianCalendar();
			date.set( Calendar.DAY_OF_MONTH, Integer.parseInt(day) );
			date.set( Calendar.MONTH, Integer.parseInt(month) - 1 );
			date.set( Calendar.YEAR, Integer.parseInt(year) );
		}
		
		recieved.setTitle(title);
		recieved.setCost(cost);
		recieved.setCurrency(curr);
		recieved.setDate(date);
		
		Intent i = new Intent(getApplicationContext(), ViewClaimActivity.class);
		
		i.putExtra("respectiveExpense", recieved);
		i.putExtra("indexOfExpense", getIntent().getIntExtra("indexOfExpense", 0));
		i.putExtra("respectiveClaim", getIntent().getSerializableExtra("respectiveClaim"));
		i.putExtra("indexOfClaim", getIntent().getIntExtra("indexOfClaim", 0));
		
		startActivity(i);
	}
	
	private void plugItOut(){
		et_title.setText(recieved.getTitle());
		if ( recieved.getDate() != null ){
			et_dd.setText( Integer.toString( recieved.getDate().get(Calendar.DAY_OF_MONTH) ) );
			et_mm.setText( Integer.toString( recieved.getDate().get(Calendar.MONTH) + 1) );
			et_yyyy.setText( Integer.toString( recieved.getDate().get(Calendar.YEAR) ) );
		}
		if ( recieved.getCost() != null ){
			et_cost.setText(Double.toString(recieved.getCost()));
		}
		et_currency.setText(recieved.getCurrency());
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
		return;
	}
}
