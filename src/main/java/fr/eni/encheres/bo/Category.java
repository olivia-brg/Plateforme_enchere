package fr.eni.encheres.bo;

public class Category {

	private int id;
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
