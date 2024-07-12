package com.andrew.BasicInheritance;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)

public class Purchase {

	@Id@GeneratedValue
	@Column(name = "purchase_id")
	private int purchaseId;
	@Column(name = "purchase_name")
	private String purchaseName;
	@Column(name = "purchase_price")
	private int purchasePrice;
	@ManyToOne
	private Account account;
	
	
	public Purchase() {
		super();
	}
public Purchase(String purchaseName, int purchasePrice, Account account) {
		
		
		this.purchaseName = purchaseName;
		this.purchasePrice = purchasePrice;
		this.account = account;
		this.account.getPurchases().add(this);
		
	}

	/*public int generatePurchaseId() {
		String str = "";
		for(int i = 0; i<3; i++) {
			str+= (int)(Math.random())*10;
		}
		return Integer.valueOf(str);
	}*/
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/*private int generatePurchaseId() {
		String strId = "";
		for(int i = 0; i<5; i++) {
			strId += Math.random()*10;
		}
		//conditions
		return Integer.valueOf(strId);
	}*/
	
	public int getId() {
		return this.purchaseId;
	}

	public void setId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getName() {
		return this.purchaseName;
	}

	public void setName(String purchaseName) {
		this.purchaseName = purchaseName;
	}

	public int getPrice() {
		return this.purchasePrice;
	}

	public void setPrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	
	
	
}

	
	

