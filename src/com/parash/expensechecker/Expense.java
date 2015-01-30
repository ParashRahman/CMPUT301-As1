package com.parash.expensechecker;

public class Expense {
	
	private String title;
	private Date date;
	private double cost;
	private String currency;
	private static int expenseId = 0;
	
	public Expense( String title, Date date, double cost,
			 String currency ){
		super();
		
		this.title = title;
		this.date = date;
		this.cost = cost;
		this.currency = currency;
	}
	
	public int getExpenseId() {
		return expenseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
	
}
