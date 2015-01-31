package com.parash.expensechecker;

import java.io.Serializable;

public class Date implements Serializable{
	private static final long serialVersionUID = -7303135306614856051L;
	
	private int day;
	private int month;
	private int year;
	
	Date (){
		day = 0;
		month = 0;
		year = 0;
	}
	
	Date( int day, int month, int year ){
		super();
		
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
