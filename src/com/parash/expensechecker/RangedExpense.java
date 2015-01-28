package com.parash.expensechecker;

public class RangedExpense extends Expense {
	
	private Date end_date;
	
	public RangedExpense( String title, Date date, Date end_date, double cost,
			 String currency ){
		super(title, date, cost, currency);
		
		this.end_date = end_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	public boolean isRanged( ){
		return true;
	}

}
