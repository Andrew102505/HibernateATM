package com.andrew.BasicInheritance;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import org.hibernate.Transaction;

public class ATMRunner {

	public static void runnerMethod(Customer runner, Session  session, org.hibernate.Transaction tx) {//need transaction object to make changes to db
		//the Customer object will be passed from either the Signup or Login method in the ProcessUser.java class
		String resume = "y";//keeps track if the user wants to continue with the program
		System.out.println("1 to view account info, 2 to open an account, 3 to make a purchase, 4 to transfer money: ");//make a transfer a purchase also
		Scanner in = new Scanner(System.in);
		int decision = in.nextInt();
		while(resume.toLowerCase().equals("y")) {
			
			if(decision == 1) {
				//Lists there name, email, accounts(with the name of the account and balance on the account), name of bank they belong to
				//should also show their purchase history
				System.out.println("Name: " + runner.getName());
				System.out.println("Email: " + runner.getEmail());
				System.out.println("Bank: " + runner.getBank().getName());
				System.out.println();
				System.out.println("Accounts: ");
				for(int i = 0; i<runner.getAccounts().size();i++) {
					System.out.println(runner.getAccounts().get(i).getName() + "   Balance: $" + runner.getAccounts().get(i).getBalance());
					System.out.println("Transactions: ");
					for(int j = 0; j<runner.getAccounts().get(i).getPurchases().size(); j++) {//iterates through the list of purchases on an account
						System.out.println(j+1 + ".  Description: " + runner.getAccounts().get(i).getPurchases().get(j).getName() + " ||   Amount: " + runner.getAccounts().get(i).getPurchases().get(j).getPrice());
					}
					
				}
				}else if (decision == 2){
					//allow the user to create a new account with the necssary files and commit it to the db
					System.out.println("Please enter a name for this account: ");
					String accountName = in.nextLine();
					in.nextLine();
					System.out.println("Please enter a balance for this account: ");
					int balance = in.nextInt();
					if(runner.getBank().getName().equals("Bank of America")) {
						BoAAccount a = new BoAAccount(accountName, balance, (BoACustomer) runner);
						//not sure if you did Account a = new BoAAccount if it would log it to the BoAAccount table or general acocunt table
						session.save(a);
						tx.commit();
					}else {
						ChaseAccount a = new ChaseAccount(accountName, balance, (ChaseCustomer) runner); 
						session.save(a);
						tx.commit();
					}
					
					System.out.println("Your account has been successfully created!");
					
					
				}else if(decision == 3) {
					
					for(int i = 0; i<runner.getAccounts().size();i++) {
						System.out.println((i+1) + ": " + runner.getAccounts().get(i).getName() + "   Balance: $" + runner.getAccounts().get(i).getBalance());
					}
					System.out.println("Enter the number next to the account you would like to make this purchase with: ");
					int accountNum = in.nextInt() - 1;
					in.nextLine();
					System.out.println("Please provide a description of your purchase: ");
					String description = in.nextLine();
					in.nextLine();
					System.out.println("Please provide a price for your purchase: ");
					int price = (int)in.nextDouble();
					//updates the account balance after the purchase
					//make the hql query to update the account balance to db, look up how to update a value using an hql query
					runner.getAccounts().get(accountNum).setBalance(runner.getAccounts().get(accountNum).getBalance()-price);
					int newBalance = runner.getAccounts().get(accountNum).getBalance()-price;
					if(runner.getBank().getName().equals("Bank of America")) {
						BoAPurchase p = new BoAPurchase(description, price, (BoAAccount)runner.getAccounts().get(accountNum));
						Query query = session.createQuery("UPDATE BoAAccount a set a.accountBalance = " + newBalance + " WHERE a.accountId = " + runner.getId());
						
						query.executeUpdate();
						session.save(p);
						tx.commit();
					}else {
						ChasePurchase p = new ChasePurchase(description, price, (ChaseAccount)runner.getAccounts().get(accountNum));
						Query query = session.createQuery("UPDATE ChaseAccount a set a.accountBalance = " + newBalance + " WHERE a.accountId = " + runner.getId());//ids are stored as ints by hibernate
						
						query.executeUpdate();
						session.save(p);
						tx.commit();
					}
					
				} else {//make a money transfer a purchase also
				
				for(int i = 0; i<runner.getAccounts().size();i++) {
					System.out.println((i+1) + ": " + runner.getAccounts().get(i).getName() + "   Balance: $" + runner.getAccounts().get(i).getBalance());
				}
				System.out.println("Enter the number next to the account you would like to make this purchase with: ");
				int accountNum = in.nextInt() - 1;
				in.nextLine();
				System.out.println("1 for deposit, 2 for withdraw: ");
				int num = in.nextInt();
				in.nextLine();
				if(num==1) {
					System.out.println("How much money would you like to deposit: ");
					int depositAmount = (int)in.nextDouble();
					runner.getAccounts().get(accountNum).setBalance(runner.getAccounts().get(accountNum).getBalance()+depositAmount);
					int newBalance = runner.getAccounts().get(accountNum).getBalance()+depositAmount;
					//make the hql query to update the account balance to db
					if(runner.getBank().getName().equals("Bank of America")) {
						BoAPurchase p = new BoAPurchase("Deposit", depositAmount, (BoAAccount)runner.getAccounts().get(accountNum));
						Query query = session.createQuery("UPDATE BoAAccount a set a.accountBalance = " + newBalance + " WHERE a.accountId = " + runner.getId());
						
						query.executeUpdate();
						session.save(p);
						tx.commit();
					}else {
						ChasePurchase p = new ChasePurchase("Deposit", depositAmount, (ChaseAccount)runner.getAccounts().get(accountNum));
						Query query = session.createQuery("UPDATE ChaseAccount a set a.accountBalance = " + newBalance + " WHERE a.accountId = " + runner.getId());
						
						query.executeUpdate();
						session.save(p);
						tx.commit();
					}
					//you need to make sure that you can update the customers balance to the db, might need to use an hql query
					//because session save of the object will not make those changes
				}else {
					System.out.println("How much money would you like to withdraw: ");
					int withdrawAmount = (-1)*((int)in.nextDouble());
					runner.getAccounts().get(accountNum).setBalance(runner.getAccounts().get(accountNum).getBalance()+withdrawAmount);
					int newBalance = runner.getAccounts().get(accountNum).getBalance()+withdrawAmount;
					if(runner.getBank().getName().equals("Bank of America")) {
					//make the hql query to update the account balance to db
					BoAPurchase p = new BoAPurchase("Withdraw", withdrawAmount, (BoAAccount)runner.getAccounts().get(accountNum));
					runner.getAccounts().get(accountNum).getPurchases().add(p);
					Query query = session.createQuery("UPDATE BoAAccount a set a.accountBalance = " + newBalance + " WHERE a.accountId = " + runner.getId());
					
					query.executeUpdate();
					session.save(p);
					tx.commit();
					}else {
					ChasePurchase p = new ChasePurchase("Withdraw", withdrawAmount, (ChaseAccount)runner.getAccounts().get(accountNum));
					runner.getAccounts().get(accountNum).getPurchases().add(p);
					Query query = session.createQuery("UPDATE ChaseAccount a set a.accountBalance = " + newBalance + " WHERE a.accountId = " + runner.getId());
					
					query.executeUpdate();
					session.save(p);
					tx.commit();
					}
			}
			
			//if they answer no then we will exit out of the while loop and out of the method and return to the main method
		}
			in.nextLine();
			System.out.println("Would you like to continue y/n: ");
			resume = in.nextLine();
			System.out.println("1 to view account info, 2 to open an account, 3 to make a purchase, 4 to transfer money: ");
			decision = in.nextInt();
			//you need to figure out this whole mapping of the bank things because you will be creating a new bank object in the table
		//each time that you pass a new bank to the constructor of an object. maybe I need to pass the existing BoA and chase objects in
		
		
}

	}
	
}
