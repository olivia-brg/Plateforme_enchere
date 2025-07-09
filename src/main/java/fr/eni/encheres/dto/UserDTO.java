package fr.eni.encheres.dto;

import jakarta.validation.constraints.*;

public class UserDTO {

    private int id;

    @Size(min = 3, max = 50, message = "Le nom d'utilisateur est obligatoire et doit contenir entre 2 et 50 caractères.")
    private String userName;

    @Size(min = 2, max = 100, message = "Le nom est obligatoire et doit contenir entre 2 et 100 caractères.")
    private String lastName;

    @Size(min = 2, max = 100, message = "Le prénom  est obligatoire et doit contenir entre 2 et 100 caractères.")
    private String firstName;

    @NotBlank(message = "Adresse email obligatoire.")
    @Email(message = "Le format de l'email est invalide.")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir exactement 10 chiffres.")
    private String phoneNumber;

    @Size(min = 5, max = 100, message = "La rue est obligatoire et doit contenir entre 5 et 100 caractères.")
    private String street;

    @Pattern(regexp = "^[0-9]{4,5}$", message = "Le code postal est obligatoire et doit contenir 4 ou 5 chiffres.")
    private String postalCode;

    @Size(min = 2, max = 100, message = "La ville est obligatoire et doit contenir entre 2 et 100 caractères.")
    private String city;


    public UserDTO(int id, String pseudo, String name, String prenom, String email, String phoneNumber, String street,
                String postalCode, String city) {

        this.id = id;
        this.userName = pseudo;
        this.lastName = name;
        this.firstName = prenom;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public UserDTO() {
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
        sb.append('}');
        return sb.toString();
    }
}
