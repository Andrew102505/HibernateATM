package com.andrew.BasicInheritance;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Embeddable
@Table(name = "boa_accounts")
public class BoAAccount extends Account{
	
	public BoAAccount() {
		super();
	}
	public BoAAccount(String name, int balance, BoACustomer customer) {
		super(name, balance, customer);
	}
}
