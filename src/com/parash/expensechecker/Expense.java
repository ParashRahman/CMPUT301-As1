package com.parash.expensechecker;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Expense implements Serializable {
	private static final long serialVersionUID = -6170730513521250678L;

	private String title;
	private GregorianCalendar date;
	private Double cost;
	private String currency;

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

	public Double getCost() {
		if (cost == null) {
			return null;
		}

		String result = String.format("%.2f", cost);
		return Double.parseDouble(result);
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String toString() {
		if (getCost() == null) {
			return title + " " + currency;
		}
		return title + " " + getCost() + " " + currency;
	}

}
