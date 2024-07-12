package com.andrew.BasicInheritance;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

@Entity
@Embeddable
public class ChaseBank extends Bank{

	
	public ChaseBank() {
		super("Chase");
	}
}
