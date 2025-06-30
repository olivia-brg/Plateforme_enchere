package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Article {
	
	private int id;
	private String name;
	private String description;
	private LocalDate auctionStartDate;
	private LocalDate auctionEndDate;
	private float minBid;
	private float actualPrice;
	private boolean isOnSale;
	private Category category;
	private Adress withdrawalAdress;

}
