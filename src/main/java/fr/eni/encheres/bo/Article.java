package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Article {
	
	private int id;
	private String name;
	private String description;
	private LocalDate auctionStartDate;
	private LocalDate auctionEndDate;
	private float minBid;
	private float StartingPrice;
	private boolean isOnSale;
	private Category category;
	private Adress withdrawalAdress;
	
//	Full constructor
	public Article(int id, String name, String description, LocalDate auctionStartDate, LocalDate auctionEndDate,
			float minBid, float actualPrice, boolean isOnSale, Category category, Adress withdrawalAdress) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.minBid = minBid;
		this.StartingPrice = actualPrice;
		this.isOnSale = isOnSale;
		this.category = category;
		this.withdrawalAdress = withdrawalAdress;
	}
//	Empty constructor
	public Article() {
	}
	
//	List setter and getter
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
	public float getMinBid() {
		return minBid;
	}
	public void setMinBid(float minBid) {
		this.minBid = minBid;
	}
	public float getActualPrice() {
		return StartingPrice;
	}
	public void setActualPrice(float actualPrice) {
		this.StartingPrice = actualPrice;
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
	

	
	
	
}
