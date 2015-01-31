package com.parash.expensechecker;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

public class Claim implements Serializable {
	private String name;
	private Date fromDate;
	private Date toDate;
	private ArrayList<Expense> list;
	private char status;
	
	public Claim( ){
		name = "";
		list = new ArrayList<Expense>();
		setStatus('I');
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
	
	public String toString(){
		String toRet = name + "\n";
		
		Log.i("meMessage", name + "sup");
		
		for ( int i = 0; i < list.size(); i++ ){
			toRet += list.get(i).getCost() + " " + list.get(i).getCurrency() + "\n";
		}
		
		return toRet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	/*
	public static void main(String [] args){
		Claim sup = new Claim("My name is john");
		Date date1 = new Date(11, 11, 1111);
		Expense e1 = new Expense( "Expense1", date1, 1.02, "GRE" );
		sup.addExpense(e1);
		System.out.println(sup.toString());
	}*/
}
