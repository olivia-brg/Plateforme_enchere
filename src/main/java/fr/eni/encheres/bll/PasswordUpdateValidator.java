package fr.eni.encheres.bll;

import fr.eni.encheres.dto.PasswordDTO;
import fr.eni.encheres.exception.ValidatePasswordUpdate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordUpdateValidator implements ConstraintValidator<ValidatePasswordUpdate, PasswordDTO> {

    @Override
    public boolean isValid(PasswordDTO pwd, ConstraintValidatorContext context) {
        if (pwd == null || (pwd.getNewPassword() == null || pwd.getNewPassword().isBlank())) {
            // Si on ne veut pas modifier le mot de passe, c’est valide.
            return true;
        }

        boolean valid = true;

        if (pwd.getOldPassword() == null || pwd.getOldPassword().isBlank()) {
            context.buildConstraintViolationWithTemplate("L'ancien mot de passe est obligatoire.")
                    .addPropertyNode("oldPassword")
                    .addConstraintViolation();
            valid = false;
        }

        if (pwd.getConfirmPassword() == null || !pwd.getConfirmPassword().equals(pwd.getNewPassword())) {
            context.buildConstraintViolationWithTemplate("La confirmation du mot de passe est incorrecte.")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            valid = false;
        }

        if (!valid) {
            context.disableDefaultConstraintViolation();
        }

        return valid;
    }
}
