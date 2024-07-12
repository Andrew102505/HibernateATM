package com.andrew.BasicInheritance;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;







public class ProcessUser {

	public static boolean validEmail(String email, String bankName, Session session) {
		String entityName = "";
		if(bankName.equals("Bank of America")) {
			entityName = "BoACustomer";
		}else {
			entityName = "ChaseCustomer";
		}
		//String className = bankName.replace(" ", "");
		//each customer of type BoA that is created is automatically mapped to a table that stores BoA customers since it is an entity
		//this is the same for other types of objects as well like BoA/Chase Purchases, and accounts(there is a table that stores all BoA accounts created)
		//Query q = session.createQuery("from " + className + "Customer where email=" + email);
		Query q = session.createQuery("from " + entityName);
		List<Customer> cs = q.list();//will hold all customers at that bank with same email as the one entered
		for(int i = 0; i<cs.size();i++) {
			if(cs.get(i).getEmail().equals(email)) {
				return false;
			}
		}
		return true;
		/*if(sameEmail.size()>0) {
			return false;
		}else {
			return true;
		}*/
	}
	
	public static boolean validPassword(String password, String bankName, Session session) {
		String entityName = "";
		if(bankName.equals("Bank of America")) {
			entityName = "BoACustomer";
		}else {
			entityName = "ChaseCustomer";
		}
		Query q = session.createQuery("from " + entityName + " where customerPassword='" + password +"'");
		List<Customer> cs = q.list();//will hold all customers at that bank with same email as the one entered
		if(cs.size()>0) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Customer Signup(Session session, org.hibernate.Transaction tx) {//need the tx because we will have to update the dm with the new user
			Scanner in = new Scanner(System.in);
			
			Query q  = session.createQuery("from Bank");//retrieves all the Bank objects in the banks table
			List<Bank> banks = q.list();
			for(Bank b : banks) {//this will print out all of the available banks for the user to choose from
				System.out.println(b.getName());
			}
			System.out.println("Enter the name of the bank you would like to join: ");
			String bankName = in.nextLine();
			
			while(!bankName.equals("Bank of America")&&!bankName.equals("Chase")) {//checks if you entered a valid bank
				System.out.println("Error: Please reenter one of the banks listed above: ");
				bankName = in.nextLine();
			}
			
			
			System.out.println("What is your full name: ");
			String name = in.nextLine();
			
			System.out.println("Enter your  Email: ");
			String email = in.nextLine();
			in.nextLine();
			
			//checks that the email entered is not already in use 
			while(validEmail(email, bankName, session)==false) {
				System.out.println("This email is already in use.");
				System.out.println("Please enter a new email: ");
				email = in.nextLine();
			}
				
			System.out.println("Create a Password(8 digits - must contain a letter, number, and @,!, or #): ");
			String password = in.nextLine();
			
			//checks that the password entered is not already in use 
			while(validPassword(password, bankName, session)==false) {
				System.out.println("This password is already in use.");
				System.out.println("Please enter a new password: ");
				password = in.nextLine();
			}
			
			//the reason that I'm making the object fully BankOfAmericaCustomer/ChaseCustomer is because I'm not sure if hibernate would still store the object to the BoA/Chase table and would save to general customer table instead if I used downcasting
			if(bankName.equals("Bank of America")) {
				q = session.createQuery("from Bank where bankName='Bank of America'");
				BoABank b = (BoABank) q.uniqueResult();
				BoACustomer runner = new BoACustomer(name, email, password, b);
				//this is where mappedBy will take care but this might be where you add a hql query to save this customer to the BoA Customers table
				session.save(runner);//now we are able to access the runners id since it's saved
				tx.commit();
				return runner;//a new BankOfAmerica Customer is created and stored to the db, and the method returns to the ATMRunner method
			}else if(bankName.equals("Chase")) {
				q = session.createQuery("from Bank where bankName='Chase'");
				ChaseBank b = (ChaseBank) q.uniqueResult();
				ChaseCustomer runner = new ChaseCustomer(name, email, password, b);
				session.save(runner);
				tx.commit();
				return runner;
			}else {
				return null;
			}
			//now you just need to look to the embeddable objects and figure out how to store the bank info in the customer table
			//the info will be similar for each customer because they only have the options of chase or bank of America.
		
				 
			
		}
		
	@SuppressWarnings("deprecation")
	public static Customer Login(Session session) {
		//now you need to get the database retrieval stuff mapped out
		Scanner in = new Scanner(System.in);
		boolean validLogin = false;
		
		while(validLogin == false) {
		Query q  = session.createQuery("from Bank");//retrieves all the Bank objects in the banks table
		List<Bank> banks = q.list();
		for(Bank b : banks) {//this will print out all of the available banks for the user to choose from
			System.out.println(b.getName());
		}
		System.out.println("Enter your bank name: ");
		String bankName = in.nextLine();
		//need to check this otherwise an error will occur stopping the program because of the hql query
		while(!bankName.equals("Bank of America")&&!bankName.equals("Chase")) {
			System.out.println("Invalid Bank Name.");
			System.out.println("Please enter a valid Bank Name from the list above: ");
			bankName = in.nextLine();
		}
		//bankName = bankName.replace(" ", "");//this is how the entity name is formatted
		
		String entityName = "";
		if(bankName.equals("Bank of America")) {
			entityName = "BoACustomer";
		}else {
			entityName = "ChaseCustomer";
		}
		System.out.println("Email: ");
		String email = in.nextLine();
		
		System.out.println("Password: ");
		String password = in.nextLine();
		
		q = session.createQuery("from " + entityName + " where customerEmail='" + email + "' and customerPassword='" + password +"'");
			//an instance of an existing BankOfAmericaCustomer is created and returned to the ATMRunner method
			if(q.uniqueResult()==null) {//no object was returned from the query
				System.out.println("Invalid Login Info");
				System.out.println("Please Try Again! ");
			}else {//don't have to change validLogin to true because the return statement will automitaclly exit the method as a whole
				if(bankName.equals("Bank of America")) {
					System.out.println("Made it");
					BoACustomer runner = (BoACustomer)q.uniqueResult();//bank is embedded into customer so it should be able to updload the BoABank object to this instance of customer
					return runner;
				}else if(bankName.equals("Chase")){
					
					ChaseCustomer runner = (ChaseCustomer)q.uniqueResult();
					return runner;
				}
				
			}
				
		}
		return null;
		
		}

}
