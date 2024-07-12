package com.andrew.BasicInheritance;

import java.util.ArrayList;
import java.util.List;

import com.andrew.BasicInheritance.Bank;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Embeddable
@Table(name = "customers")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Customer {
	
	@Id@GeneratedValue
	@Column(name = "customerId")
	private int customerId;
	@Column(name = "customer_name")
	private String customerName;
	@ManyToOne
	private Bank customerBank;
	@OneToMany(mappedBy = "accountCustomer")
	private List<Account> customerAccounts = new ArrayList<>();
	
	@Column(name = "customer_email") 
	private String customerEmail;
	@Column(name = "customer_password")
	private String customerPassword;
	
	
	public String getEmail() {  
		return this.customerEmail;
	}

	public void setEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public Customer() {
		super();
	}
	public Customer(String customerName, String customerEmail, String customerPassword, Bank customerBank) {
		//this.customerId = generateCustomerId();
		this.customerName =customerName;
		this.customerEmail = customerEmail;
		this.customerPassword = customerPassword;
		this.customerBank = customerBank;
		this.customerBank.getCustomers().add(this);
		this.customerBank.getCustomers().add(this);
		//this.customerAccounts = new ArrayList<Account>(); initialized above
		
	}
	
	/*public int generateCustomerId() {
		String str = "";
		for(int i = 0; i<3; i++) {
			str+= (int)(Math.random())*10;
		}
		return Integer.valueOf(str);
	}*/
	


	public int getId() {
		return customerId;
	}

	public void setId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return this.customerName;
	}

	public Bank getBank() {
		return this.customerBank;
	}

	public void setBank(Bank customerBank) {
		this.customerBank = customerBank;
	}

	public List<Account> getAccounts() {
		return this.customerAccounts;
	}

	public void setAccount(List<Account> accounts) {
		this.customerAccounts = accounts;
	}
	
}


	
