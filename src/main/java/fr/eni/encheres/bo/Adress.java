package fr.eni.encheres.bo;

public class Adress {
	
	private int deliveryAdressId;
	private String street;
	private String postalCode;
	private String city;
	
//	Full constructor
	public Adress( int deliveryAdressId, String street, String postalCode, String city) {
		
		this.deliveryAdressId = deliveryAdressId;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}


//	Empty constructor
	public Adress() {
	}

	public int getDeliveryAdressId() {
		return deliveryAdressId;
	}

	public void setDeliveryAdressId(int deliveryAdressId) {
		this.deliveryAdressId = deliveryAdressId;
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

	
//	List getter and setter


}
