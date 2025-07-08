package fr.eni.encheres.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class Bid {

	@NotNull(message = "La date de l'enchère est obligatoire.")
	@PastOrPresent(message = "La date de l'enchère ne peut pas être dans le futur.")
	private LocalDate bidDate;

	@Positive(message = "Le montant de l'enchère doit être strictement positif.")
	private float bidAmount;

	@Valid
	@NotNull(message = "L'article est obligatoire.")
	private Article article;


//	Full constructor
	public Bid(LocalDate auctionDate, float auctionAmount, Article article) {
	
		this.bidDate = auctionDate;
		this.bidAmount = auctionAmount;
		this.article = article;
	}

	public Bid() {
	}


//	List setter and getter
	public LocalDate getBidDate() {
		return bidDate;
	}
	public void setBidDate(LocalDate auctionDate) {
		this.bidDate = auctionDate;
	}

	public float getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(float auctionAmount) {
		this.bidAmount = auctionAmount;
	}

	public Article getArticle() {return article;}
	public void setArticle(Article article) {
		this.article = article;
	}


	
}
