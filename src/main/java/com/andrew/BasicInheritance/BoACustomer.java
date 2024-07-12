package com.andrew.BasicInheritance;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Embeddable 
@Table(name = "boa_customers")
public class BoACustomer extends Customer{
	public BoACustomer() {
		super();
	}
	
	public BoACustomer(String name, String email, String password, BoABank bank) {
		
		super(name, email, password, bank);
	}
}
  