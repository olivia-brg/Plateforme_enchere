package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Article {
	// Attribut
	private int id;
	private String name;
	private String description;
	private LocalDate auctionStartDate;
	private LocalDate auctionEndDate;
	private float soldPrice;
	private float startingPrice;
	private boolean isOnSale;
	//association
	private Category category;
	private Adress withdrawalAdress;
	private User user;
	
//	Full constructor
	public Article(int id, String name, String description, LocalDate auctionStartDate, LocalDate auctionEndDate,
			float minBid, float actualPrice, boolean isOnSale, Category category, Adress withdrawalAdress, User user) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.soldPrice = minBid;
		this.startingPrice = actualPrice;
		this.isOnSale = isOnSale;
		this.category = category;
		this.withdrawalAdress = withdrawalAdress;
		this.user = user;
	}
//	Empty constructor
	public Article() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getAuctionStartDate() {
		return auctionStartDate;
	}
	public void setAuctionStartDate(LocalDate auctionStartDate) {
		this.auctionStartDate = auctionStartDate;
	}
	public LocalDate getAuctionEndDate() {
		return auctionEndDate;
	}
	public void setAuctionEndDate(LocalDate auctionEndDate) {
		this.auctionEndDate = auctionEndDate;
	}
	public float getSoldPrice() {
		return soldPrice;
	}
	public void setSoldPrice(float soldPrice) {
		this.soldPrice = soldPrice;
	}
	public float getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(float startingPrice) {
		this.startingPrice = startingPrice;
	}
	public boolean isOnSale() {
		return isOnSale;
	}
	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Adress getWithdrawalAdress() {
		return withdrawalAdress;
	}
	public void setWithdrawalAdress(Adress withdrawalAdress) {
		this.withdrawalAdress = withdrawalAdress;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
//	List setter and getter


	
	
	
}
