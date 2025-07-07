package fr.eni.encheres.dal;

import jakarta.validation.Valid;

public class ProfileFormDTO {
    @Valid
    private UserDTO user;

    @Valid
    private PasswordDTO passwordModification;

    public UserDTO getUser() {
        return user;
    }
    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PasswordDTO getPasswordModification() {
        return passwordModification;
    }
    public void setPasswordModification(PasswordDTO passwordModification) {
        this.passwordModification = passwordModification;
    }
}
