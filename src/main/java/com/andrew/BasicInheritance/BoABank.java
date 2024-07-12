package com.andrew.BasicInheritance;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Embeddable
public class BoABank extends Bank{

	
	
	public BoABank() {
		super("Bank of America");
	}
}
