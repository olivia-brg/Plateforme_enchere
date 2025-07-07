package fr.eni.encheres.exception;

import fr.eni.encheres.bll.PasswordUpdateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordUpdateValidator.class)
@Documented
public @interface ValidatePasswordUpdate {
    String message() default "Les champs du mot de passe ne sont pas valides.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
