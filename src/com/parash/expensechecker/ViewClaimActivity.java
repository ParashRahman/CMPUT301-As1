package com.parash.expensechecker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ViewClaimActivity extends Activity{
	private ArrayList<Expense> listOfExpenses;
	private ArrayAdapter<Expense> adapter;
	private Claim theClaim;
	
	private Button addExpense;
	private Button b_return;
	private ListView lv_expenses;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
				
        setContentView(R.layout.view_claim);
		
        lv_expenses = (ListView) findViewById( R.id.lv_expenses );
        addExpense = (Button) findViewById(R.id.b_addExpense);
        b_return = (Button) findViewById(R.id.b_returnClaim);
        
        addExpense.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), EditExpenseActivity.class);
				
				Expense newExpense = new Expense();
				listOfExpenses.add(newExpense);
				
				i.putExtra("respectiveExpense", newExpense);
				i.putExtra("indexOfExpense", listOfExpenses.size()-1);
				
				startActivity(i);
			}
		});
        
        b_return.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				
				i.putExtra("respectiveClaim", theClaim);
				i.putExtra("indexOfClaim", getIntent().getIntExtra("indexOfClaim", 0));
				
				startActivity(i);
			}
		});
        
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		theClaim = (Claim) getIntent().getSerializableExtra("respectiveClaim");
		listOfExpenses = theClaim.getList();
		
		adapter = new ArrayAdapter<Expense>(this, R.layout.expense_list_line, R.id.tv_expenseList, listOfExpenses );
		lv_expenses.setAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
