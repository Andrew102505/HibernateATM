package com.andrew.BasicInheritance;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Embeddable
@Table(name = "chase_customers")
public class ChaseCustomer extends Customer{
	
	public ChaseCustomer() {
		super();
	}
	public ChaseCustomer(String name, String email, String password, ChaseBank bank){
		super(name, email, password, bank);
	}

}
