package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Bid {

	private LocalDate bidDate;
	private float bidAmount;
	private Article article;
	
//	Full constructor
	public Bid(LocalDate auctionDate, float auctionAmount, Article article) {
	
		this.bidDate = auctionDate;
		this.bidAmount = auctionAmount;
		this.article = article;
	}
	
//	Empty constructor
	public Bid() {
	}
	
//	List setter and getter
	
	public LocalDate getAuctionDate() {
		return bidDate;
	}

	public void setAuctionDate(LocalDate auctionDate) {
		this.bidDate = auctionDate;
	}

	public float getAuctionAmount() {
		return bidAmount;
	}

	public void setAuctionAmount(float auctionAmount) {
		this.bidAmount = auctionAmount;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
		

	
}
