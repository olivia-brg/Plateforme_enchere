package fr.eni.encheres.dto;

import fr.eni.encheres.exception.ValidatePasswordUpdate;

@ValidatePasswordUpdate
public class PasswordDTO {


    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public PasswordDTO(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public PasswordDTO(String oldPassword, String firstNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = firstNewPassword;
    }

    public PasswordDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
