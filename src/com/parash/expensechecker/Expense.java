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
