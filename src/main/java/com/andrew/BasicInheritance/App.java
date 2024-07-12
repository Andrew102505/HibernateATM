package com.andrew.BasicInheritance;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import com.andrew.BasicInheritance.Bank;
import com.andrew.BasicInheritance.Customer;
import org.hibernate.query.Query;

/**
 * Hello world!
 *
 */
//make sure to go to the pom.xml file and add the mapping tags for all program files in your project
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Configuration con = new Configuration().configure().addAnnotatedClass(Customer.class).addAnnotatedClass(Bank.class).addAnnotatedClass(Account.class).addAnnotatedClass(Purchase.class).addAnnotatedClass(BoABank.class).addAnnotatedClass(ChaseBank.class).addAnnotatedClass(BoACustomer.class).addAnnotatedClass(ChaseCustomer.class).addAnnotatedClass(BoAAccount.class).addAnnotatedClass(ChaseAccount.class).addAnnotatedClass(BoAPurchase.class).addAnnotatedClass(ChasePurchase.class);//.addAnnotatedClass(Account.class).addAnnotatedClass(Purchase.class);
    	SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();
       
        Transaction tx = (Transaction) session.beginTransaction();
        
       //BoABank boa = new BoABank();
       //ChaseBank chase = new ChaseBank();
       //session.save(boa);
       //session.save(chase);
       
       
        //BoAPurchase p  = new BoAPurchase("Food", 25, (BoAAccount)session.get(BoAAccount.class, 1));
        //session.save(p);
        /*BoAAccount a = (BoAAccount)session.get(BoAAccount.class, 1);
        List<Purchase> purchases = a.getPurchases();
        for(int i = 0; i<purchases.size();i++) {
        	System.out.println(purchases.get(i).getName());
        }*/
        /*BoACustomer c = (BoACustomer)session.get(BoACustomer.class, 1);
        List<Account> accounts = c.getAccounts();
        for(int i = 0; i<accounts.size();i++) {
        	System.out.println(accounts.get(i).getName());
        }*/
        
       Query q = session.createQuery("from Bank");
        List<Bank> banks = q.list();//will return all banks from the query as a list
        if(banks.size()==0) {
        	BoABank boa = new BoABank();
            ChaseBank chase = new ChaseBank();
            session.save(boa);
            session.save(chase);
            tx.commit();
            //might need to commit after this - tx.commit();
        }
       // BoACustomer c = new BoACustomer("Tom Johnson", "TomJohn@gmail.com", "TommyJ25", (BoABank)session.get(BoABank.class, 1));
        //session.save(c);
        //BoAAccount a = new BoAAccount("Toms Main", 3000, (BoACustomer)session.get(BoACustomer.class, 1));
        //session.save(a);
        //int newBalance = 2000;
        //Query query = session.createQuery("UPDATE BoAAccount a set a.accountBalance = " + newBalance + " WHERE a.accountId = " + 1);
		
		//query.executeUpdate();
		//session.save(p);
		
        /*String entityName = "BoACustomer";
        String email = "TomJohn@gmail.com";
        String password = "TommyJ25";
        q = session.createQuery("from " + entityName + " where customerEmail='" + email + "' and customerPassword='" + password +"'");
      BoACustomer tom = (BoACustomer) q.uniqueResult();
      System.out.println(tom.getName());
      */
        
        Scanner in = new Scanner(System.in);
        System.out.println("1 for new member, 2 for existing member: ");
        int decision = in.nextInt();
        
        if(decision==1) {
    	ATMRunner.runnerMethod(ProcessUser.Signup(session, tx), session, tx);
    }else{
    	ATMRunner.runnerMethod(ProcessUser.Login(session), session, tx);

}


        
        tx.commit();
    }
}
