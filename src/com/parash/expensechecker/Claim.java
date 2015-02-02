package com.parash.expensechecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Claim implements Serializable {
	private static final long serialVersionUID = -7441590390307524016L;
	
	private String name;
	private GregorianCalendar fromDate;
	private GregorianCalendar toDate;
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

	public ArrayList<Expense> getList(){
		return list;
	}
	
	public void setList( ArrayList<Expense> l ){
		list = l;
	}
	
	public String toString(){
		String toRet = name + "\n";
				
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
