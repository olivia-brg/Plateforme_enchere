package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Category {

	private int id;

	@NotBlank(message = "Le nom est obligatoire.")
	@Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères.")
	private String name;
	
//	Full constructor
	public Category(int id, String name) {

		this.id = id;
		this.name = name;
	}
	
//	Empty constructor
	public Category() {
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
	
	
	
}
