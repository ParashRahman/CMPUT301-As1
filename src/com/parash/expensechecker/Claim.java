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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class Claim implements Serializable {
	private static final long serialVersionUID = -7441590390307524016L;
	
	public static final String IN_PROGRESS = "In Progress";
	public static final String SUBMITTED = "Submitted";
	public static final String APPROVED = "Approved";
	public static final String RETURNED = "Returned";
	
	private String name;
	private GregorianCalendar fromDate;
	private GregorianCalendar toDate;
	private ArrayList<Expense> list;
	private String status;
	
	public Claim( ){
		name = "";
		list = new ArrayList<Expense>();
		setStatus(IN_PROGRESS);
	}
	
	public void addExpense( Expense exp ){
		list.add( exp );
	}

	public ArrayList<Expense> getList(){
		return list;
	}
	
	public void setList( ArrayList<Expense> l ){
		list = l;
	}
	
	public String toString(){
		String toRet = status + "\n" + name + "\n";
		
		ArrayList<String> currencies = new ArrayList<String>();
		ArrayList<Double> totals = new ArrayList<Double>();
		
		for ( Expense e : list ){
			if ( e.getCurrency().trim() != "" && e.getCost() != null ){
				int index = currencies.indexOf( e.getCurrency().toUpperCase() );
				if ( index == -1 ){
					currencies.add(e.getCurrency().toUpperCase());
					totals.add(e.getCost());
				} else {
					totals.set(index, totals.get(index) + e.getCost());
				}
			}
		}
		
		for ( int i = 0; i < currencies.size(); i++ ){
			toRet += totals.get(i) + " " + currencies.get(i) + "\n";
		}
		
		return toRet;
	}
	
	public String claimSummary(){
		String toRet = "Claim: " + name + "\n" + "Status: " + status + "\n"
				+ "Expenses: " + "\n";
		for ( Expense e : list ){
			toRet += e.toString() + "\n";
		}
		
		return toRet;
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GregorianCalendar getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(GregorianCalendar fdate) {
		this.fromDate = fdate;
	}

	public GregorianCalendar getToDate() {
		return toDate;
	}
	
	public void setToDate(GregorianCalendar toDate) {
		this.toDate = toDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String string) {
		this.status = string;
	}
	
}
