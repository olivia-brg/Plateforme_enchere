package fr.eni.encheres.bo;

import java.util.List;

public class User {
	
	
//	Attributes of user
	private int id;
	private String pseudo;
	private String name;
	private String prenom;
	private String email;
	private int phoneNumber;
	private String street;
	private String postalCode;
	private String city;
	private String password;
	private float credit;
	private boolean admin;
	private List<Bid> bids;
	private List<Article> articles;
	
}
