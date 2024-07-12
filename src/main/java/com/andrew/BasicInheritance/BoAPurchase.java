package com.andrew.BasicInheritance;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "boa_purchases")
public class BoAPurchase extends Purchase{
	
	public BoAPurchase() {
		super(); 
	}
	
	public BoAPurchase(String name, int price, BoAAccount account) {
		super(name, price, account);
	}
	
}
