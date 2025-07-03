package fr.eni.encheres.bo;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

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
	private LocalDate auctionStartDate;

	@NotNull(message = "La date de fin de l'enchère est obligatoire.")
	@FutureOrPresent(message = "La date de fin de l'enchère doit être dans le présent ou le futur.")
	private LocalDate auctionEndDate;

	@Positive(message = "Le prix de départ doit être un nombre positif.")
	private float startingPrice;

	private float soldPrice; // Peut être 0 si pas encore vendu, donc pas de validation ici.

	private boolean isOnSale;

	// Associations
	@NotNull(message = "La catégorie est obligatoire.")
	private Category category;

	@NotNull(message = "L'adresse de retrait est obligatoire.")
	private Adress withdrawalAdress;

	@NotNull(message = "L'utilisateur est obligatoire.")
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
}
