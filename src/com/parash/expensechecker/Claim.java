package com.parash.expensechecker;

import java.util.ArrayList;

public class Claim {
	private String name;
	private ArrayList<Expense> list;
	
	public Claim( String name ){
		this.name = name;
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
	
	/*
	public static void main(String [] args){
		Claim sup = new Claim("My name is john");
		Date date1 = new Date(11, 11, 1111);
		Expense e1 = new Expense( "Expense1", date1, 1.02, "GRE" );
		sup.addExpense(e1);
		System.out.println(sup.toString());
	}*/
}
