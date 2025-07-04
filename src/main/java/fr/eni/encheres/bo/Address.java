package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Adress {

	private int deliveryAddressId;

	@NotBlank(message = "La rue ne peut pas être vide.")
	@Size(min = 5, max = 100, message = "La rue doit contenir entre 5 et 100 caractères.")
	private String street;

	@NotBlank(message = "Le code postal ne peut pas être vide.")
	@Size(min = 4, max = 5, message = "Le code postal doit contenir entre 4 et 5 chiffres.")
	@Pattern(regexp = "\\d{4,5}", message = "Le code postal doit contenir uniquement des chiffres (4 à 5).")
	private String postalCode;

	@NotBlank(message = "La ville ne peut pas être vide.")
	@Size(min = 2, max = 100, message = "La ville doit contenir entre 2 et 100 caractères.")
	private String city;
	
//	Full constructor
	public Adress( int deliveryAdressId, String street, String postalCode, String city) {
		
		this.deliveryAddressId = deliveryAdressId;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}

	public Adress() {
	}


//	List setter and getter
	public int getDeliveryAddressId() {
		return deliveryAddressId;
	}
	public void setDeliveryAddressId(int deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
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

}
