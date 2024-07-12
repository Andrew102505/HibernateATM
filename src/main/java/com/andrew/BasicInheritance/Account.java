package com.andrew.BasicInheritance;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity//since this is marked as an entity when I create a new BoA account in the ATM Runner a separate table for all BoA accounts will be created with all of the columns except for the purchases but this account info will be logged on the BoA purchase table when a purchase is made with this BoA account because when I create that purchase I will associate it with an account(this BoA account that the runner is using)
@Embeddable//because account info will be stored within an entry for a purchase
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Account {
	
	
	
		
		@Id@GeneratedValue//you can access the generated id of an object after it has been saved
		@Column(name = "account_id")
		private int accountId;
		@Column(name = "account_name")
		private String accountName;
		@Column(name = "account_balance")
		private int accountBalance;
		@ManyToOne
		private Customer accountCustomer;
		@OneToMany(mappedBy = "account")//one account(belongs to a customer) can have many purchases
		private List<Purchase> accountPurchases = new ArrayList<>(); 
		
		
		//duplicate foreign key constraint because bank id will serve as a foreign key for a bank in two 
		//different tables (account and customer table) so it can only be a foreign key for one table so 
		//we will just link the bank to the customer and we can link an account to the bank through the customer since the account is associated with a customer and customer with a specific bank so that account is also associated with that bank
		//@ManyToOne//**you might not need this relation because the account will be under a customer and that customer will be tied to the bank, the only thing I'm confused about is that we have subclasses for the different types of accounts
		//private Bank accountBank;
		
		public Account() {
			super();
		}
		public Account(String accountName, int accountBalance, Customer accountCustomer) {
			
			
			this.accountName = accountName;
			this.accountBalance = accountBalance;
			
			this.accountCustomer = accountCustomer;
			
			this.accountCustomer.getAccounts().add(this);
			//this.accountPurchases = new ArrayList<Purchase>();
			
			//this.accountCustomer.getAccounts().add(this);
			
		}
		
		/*public int generateAccountId() {
			String str = "";
			for(int i = 0; i<3; i++) {
				str+= (int)(Math.random())*10;
			}
			return Integer.valueOf(str);
		}*/
		
		

		/*private int generateAccountId() {
			String strId = "";
			for(int i = 0; i<5; i++) {
				strId += Math.random()*10;
			}
			//conditions
			return Integer.valueOf(strId);
		}*/
		
		public int getId() {
			return this.accountId;
		}
		public void setId(int accounId) {
			this.accountId = accountId;
		}
		public String getName() {
			return this.accountName;
		}
		public void setName(String accountName) {
			this.accountName = accountName;
		}
		public int getBalance() {
			return this.accountBalance;
		}
		public void setBalance(int accountBalance) {
			this.accountBalance = accountBalance;
		}
		public List<Purchase> getPurchases() {
			return this.accountPurchases;
		}
		public void setPurchases(List<Purchase> purchases) {
			//instead of using this method you will most likely use the get method and then add a single purchase to the arraylist
			this.accountPurchases = purchases;
		}
		


	

}
