package fr.eni.encheres.bo;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class Article {
	// Attribut
	private int id;

	@NotBlank(message = "Le nom ne peut pas être vide.")
	@Size(min = 5, max = 100, message = "Le nom doit contenir entre 5 et 100 caractères.")
	private String name;

	@NotBlank(message = "La description ne peut pas être vide.")
	@Size(max = 250, message = "La description ne peut pas dépasser 250 caractères.")
	private String description;

	@NotNull(message = "La date de début de l'enchère est obligatoire.")
	@FutureOrPresent(message = "La date de début de l'enchère doit être dans le présent ou le futur.")
	private LocalDateTime auctionStartDate;

	@NotNull(message = "La date de fin de l'enchère est obligatoire.")
	@FutureOrPresent(message = "La date de fin de l'enchère doit être dans le présent ou le futur.")
	private LocalDateTime auctionEndDate;

	@Positive(message = "Le prix de départ doit être un nombre positif.")
	private float startingPrice;

	private float soldPrice; // Peut être 0 si pas encore vendu, donc pas de validation ici.

	private boolean isOnSale;
	private String imageURL;
	//association
	@NotNull(message = "La catégorie est obligatoire.")
	private Category category;

	@NotNull(message = "L'adresse de retrait est obligatoire.")
	private Address withdrawalAddress;

	@NotNull(message = "L'utilisateur est obligatoire.")
	private User user;
	
//	Full constructor
	public Article(int id, String name, String description, LocalDateTime auctionStartDate, LocalDateTime auctionEndDate,
				   float minBid, float actualPrice, boolean isOnSale, Category category, Address withdrawalAddress, User user) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.soldPrice = minBid;
		this.startingPrice = actualPrice;
		this.isOnSale = isOnSale;
		this.category = category;
		this.withdrawalAddress = withdrawalAddress;
		this.user = user;
	}

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

	public Address getWithdrawalAddress() {
		return withdrawalAddress;
	}
	public void setWithdrawalAddress(Address withdrawalAddress) {
		this.withdrawalAddress = withdrawalAddress;
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
				", withdrawalAddress=" + withdrawalAddress +
				", user=" + user +
				'}';
	}
}
