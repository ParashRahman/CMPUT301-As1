package com.parash.expensechecker;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Expense implements Serializable {
	private static final long serialVersionUID = -6170730513521250678L;
	
	private String title;
	private GregorianCalendar date;
	private double cost;
	private String currency;
	
	public Expense( String title, GregorianCalendar date, double cost,
			 String currency ){
		super();
		
		this.title = title;
		this.date = date;
		this.cost = cost;
		this.currency = currency;
	}
	
	public Expense() {
		// TODO Auto-generated constructor stub
		title = "";
		currency = "";
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public boolean costIsInt( ){
		return ( (double)( (int) cost ) ) == cost;
	}
	
	public String toString(){
		return title + " " + cost + " " + currency;
	}
	
}
