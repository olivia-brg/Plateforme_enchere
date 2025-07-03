package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Article {
	// Attribut
	private int id;
	private String name;
	private String description;
	private LocalDateTime auctionStartDate;
	private LocalDateTime auctionEndDate;
	private float soldPrice;
	private float startingPrice;
	private boolean isOnSale;
	private String imageURL;
	//association
	private Category category;
	private Adress withdrawalAdress;
	private User user;
	
//	Full constructor
	public Article(int id, String name, String description, LocalDateTime auctionStartDate, LocalDateTime auctionEndDate,
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
	public LocalDateTime getAuctionStartDate() {
		return auctionStartDate;
	}
	public void setAuctionStartDate(LocalDateTime auctionStartDate) {
		this.auctionStartDate = auctionStartDate;
	}
	public LocalDateTime getAuctionEndDate() {
		return auctionEndDate;
	}
	public void setAuctionEndDate(LocalDateTime auctionEndDate) {
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
	//Associations
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
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	//	List setter and getter


	@Override
	public String toString() {
		return "Article{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", auctionStartDate=" + auctionStartDate +
				", auctionEndDate=" + auctionEndDate +
				", soldPrice=" + soldPrice +
				", startingPrice=" + startingPrice +
				", isOnSale=" + isOnSale +
				", category=" + category +
				", withdrawalAdress=" + withdrawalAdress +
				", user=" + user +
				'}';
	}
}
