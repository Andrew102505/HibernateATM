package com.andrew.BasicInheritance;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Embeddable
@Table(name = "chase_accounts")
public class ChaseAccount extends Account{
	
	public ChaseAccount() {
		super();
	}
	public ChaseAccount(String name, int balance, ChaseCustomer customer) {
		super(name, balance, customer);
	}
	
}
