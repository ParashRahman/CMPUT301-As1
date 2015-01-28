package com.parash.expensechecker;

import java.util.ArrayList;

public class ExpenseList {
	private ArrayList<Expense> list;
	
	public ExpenseList( ){
		 list = new ArrayList<Expense>();
	}
	
	public void addExpense( Expense exp ){
		list.add( exp );
	}
	
	public void removeExpense( int expId ){
		for ( int i = 0; i < list.size(); i++ ){
			if ( list.get(i).getExpenseId() == expId ){
				list.remove( i );
				break;
			}
		}
	}

	public ArrayList<Expense> getList(){
		return list;
	}
}
