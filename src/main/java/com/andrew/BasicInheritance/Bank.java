package com.andrew.BasicInheritance;

import java.util.ArrayList;
import java.util.List;

import com.andrew.BasicInheritance.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Embeddable
@Table(name = "banks")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Bank {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "bank_id")
	private int bankId;
	@Column(name = "bank_name")
	private String bankName;
	
	@OneToMany(mappedBy = "customerBank")
	private List<Customer> customers = new ArrayList<>();
	//@OneToMany(mappedBy = "accountBank")
	//private ArrayList<Account> bankAccounts;
	public Bank() {
		super();
	}
	public Bank(String bankName) {
		//this.bankId = generateBankId();
		this.bankName = bankName;
		//this.customers = new ArrayList<Customer>();
		//this.bankAccounts = new ArrayList<Account>();
		
	}
	
	/*private int generateBankId() {
		String strId = "";
		for(int i = 0; i<5; i++) {
			strId += Math.random()*10;
		}
		//conditions
		return Integer.valueOf(strId);
	}*/
	public int generateBankId() {
		String str = "";
		for(int i = 0; i<3; i++) {
			str+= (int)(Math.random())*10; 
		}
		return Integer.valueOf(str);
	}
	
	public int getId() {
		return this.bankId;
	}

	public void setId(int bankId) {
		this.bankId = bankId;
	}

	public String getName() {
		return this.bankName;
	}

	public void setName(String bankName) {
		this.bankName = bankName;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	@Override
	public String toString() {
		return "Bank [bankId=" + bankId + ", bankName=" + bankName + "]";
	}
}
