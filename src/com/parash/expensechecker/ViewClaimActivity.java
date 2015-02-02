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

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ViewClaimActivity extends Activity{
	private ArrayList<Expense> listOfExpenses;
	private ArrayAdapter<Expense> adapter;
	private Claim theClaim;
	
	private Button addExpense;
	private Button b_return;
	private ListView lv_expenses;
	private TextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
				
        setContentView(R.layout.view_claim);
		
        tv_title = (TextView) findViewById(R.id.tv_claimTitle);
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
				i.putExtra("respectiveClaim", theClaim);
				i.putExtra("indexOfClaim", getIntent().getIntExtra("indexOfClaim", 0));
				
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
        
        
		//http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
		//http://stackoverflow.com/questions/7201159/is-using-menuitem-getitemid-valid-in-finding-which-menuitem-is-selected-by-use
		//http://stackoverflow.com/questions/4554435/how-to-get-the-index-and-string-of-the-selected-item-in-listview-in-android
		lv_expenses.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
		    public void onItemClick(AdapterView<?> parentView, View childView, final int position, long id) 
		    {
		    	PopupMenu popup = new PopupMenu(ViewClaimActivity.this, childView);
                popup.getMenuInflater().inflate(R.menu.expense_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                	public boolean onMenuItemClick(MenuItem item) {
                		
                    	switch (item.getItemId()) {
                        case R.id.edit_expense:
                        	Intent i = new Intent(getApplicationContext(), EditExpenseActivity.class);
            				
            				Expense expense = listOfExpenses.get(position);
            				
            				i.putExtra("indexOfClaim", getIntent().getIntExtra("indexOfClaim", 0));
            				i.putExtra("respectiveClaim", theClaim);
            				i.putExtra("respectiveExpense", expense);
            				i.putExtra("indexOfExpense", position );
            				
            				startActivity(i);                      	
                        	
                            return true;
                        
                        case R.id.delete_expense:
                        	listOfExpenses.remove(position);
                        	adapter = new ArrayAdapter<Expense>(ViewClaimActivity.this, R.layout.expense_list_line, R.id.tv_expenseList, listOfExpenses );
                    		lv_expenses.setAdapter(adapter);
                            return true;
                        
                        default:;
                        }
                        return true;
                    }

                });
                
                popup.show(); 
		        
		    }
		    public void onNothingSelected(AdapterView<?> parentView) 
		    {
		    }
		});
		
  
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		theClaim = (Claim) getIntent().getSerializableExtra("respectiveClaim");
		listOfExpenses = theClaim.getList(); 
		tv_title.setText(theClaim.getName());
		
		boolean expenseInExtras = getIntent().getExtras().containsKey("indexOfExpense") && getIntent().getExtras().containsKey("respectiveExpense");
		
		if ( expenseInExtras ){
			int dex = getIntent().getIntExtra("indexOfExpense", 0);
			Expense e = (Expense) getIntent().getSerializableExtra("respectiveExpense");
			
			listOfExpenses.set(dex, e);
		}
		
		adapter = new ArrayAdapter<Expense>(this, R.layout.expense_list_line, R.id.tv_expenseList, listOfExpenses );
		lv_expenses.setAdapter(adapter);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
		return;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		theClaim.setList(listOfExpenses);
	}
}
