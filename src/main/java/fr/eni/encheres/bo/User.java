package fr.eni.encheres.bo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class User {

	private int id;

	@NotBlank(message = "Le nom d'utilisateur est obligatoire.")
	@Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères.")
	private String userName;

	@NotBlank(message = "Le nom est obligatoire.")
	@Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères.")
	private String lastName;

	@NotBlank(message = "Le prénom est obligatoire.")
	@Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères.")
	private String firstName;

	@NotBlank(message = "L'adresse email est obligatoire.")
	@Email(message = "Le format de l'email est invalide.")
	private String email;

	@Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir exactement 10 chiffres.")
	private String phoneNumber;

	@NotBlank(message = "La rue est obligatoire.")
	@Size(min = 5, max = 100, message = "La rue doit contenir entre 5 et 100 caractères.")
	private String street;

	@NotBlank(message = "Le code postal est obligatoire.")
	@Pattern(regexp = "^[0-9]{4,5}$", message = "Le code postal doit contenir 4 ou 5 chiffres.")
	private String postalCode;

	@NotBlank(message = "La ville est obligatoire.")
	@Size(min = 2, max = 100, message = "La ville doit contenir entre 2 et 100 caractères.")
	private String city;

//	@NotBlank(message = "Le mot de passe est obligatoire.")
//	@Size(min = 8, max = 100, message = "Le mot de passe doit contenir entre 8 et 100 caractères.")
	private String password;

	@PositiveOrZero(message = "Le crédit ne peut pas être négatif.")
	private float credit;

	private boolean isAdmin;

	@Valid
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
		final StringBuilder sb = new StringBuilder("User{");
		sb.append("id=").append(id);
		sb.append(", userName='").append(userName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", phoneNumber='").append(phoneNumber).append('\'');
		sb.append(", street='").append(street).append('\'');
		sb.append(", postalCode='").append(postalCode).append('\'');
		sb.append(", city='").append(city).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", credit=").append(credit);
		sb.append(", isAdmin=").append(isAdmin);
		sb.append(", bids=").append(bids);
		sb.append(", articles=").append(articles);
		sb.append('}');
		return sb.toString();
	}
}
