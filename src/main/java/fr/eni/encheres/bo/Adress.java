package fr.eni.encheres.bo;

public class Adress {
	
	private int id;
	private String street;
	private String postalCode;
	private String city;
	
//	Full constructor
	public Adress(int id,String street, String postalCode, String city) {
		
		this.id = id;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}

//	Empty constructor
	public Adress() {
	}

	
//	List getter and setter
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
