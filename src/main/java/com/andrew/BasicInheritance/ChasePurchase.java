package com.andrew.BasicInheritance;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "chase_purchases")
public class ChasePurchase extends Purchase{

	public ChasePurchase() {
		super();
	}
	
	public ChasePurchase(String name, int price, ChaseAccount account) {
		super(name, price, account);
	}
}
