package fr.eni.encheres.bo;

import java.util.List;

public class User {

	private int id;
	private String userName;
	private String lastName;
	private String firstName;
	private String email;
	private String phoneNumber;
	private String street;
	private String postalCode;
	private String city;
	private String password;
	private float credit;
	private boolean isAdmin;
	private List<Bid> bids;
	private List<Article> articles;

//	Constructor without articles and bids
	public User(int id, String pseudo, String name, String prenom, String email, String phoneNumber, String street,
			String postalCode, String city, String password, float credit, boolean admin) {
	
		this.id = id;
		this.userName = pseudo;
		this.lastName = name;
		this.firstName = prenom;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.password = password;
		this.credit = credit;
		this.isAdmin = admin;
	}

	public User() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Bid> getBids() {
		return bids;
	}
	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				", email='" + email + '\'' +
				", credit=" + credit +
				", isAdmin=" + isAdmin +
				'}';
	}
}
