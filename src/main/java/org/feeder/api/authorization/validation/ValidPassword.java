package org.feeder.api.authorization.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * ^                 # start-of-string (?=.*[0-9])       # a digit must occur at least once
 * (?=.*[a-z])       # a lower case letter must occur at least once (?=.*[A-Z])       # an upper
 * case letter must occur at least once (?=\S+$)          # no whitespace allowed in the entire
 * string .{8,}             # anything, at least eight places though $                 #
 * end-of-string
 * <p>
 * User's password. Should contain at least one digit, one lower case letter, one upper case letter.
 * Cannot contain whitespaces. Minimum 8 characters long
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
    message = "Password is too weak. It should be at least 8 symbols, at least 1 digit, at least 1 lower&upper case letter and no whitespaces."
)
@Size(min = 8, max = 30)
public @interface ValidPassword {

  String message() default "Invalid Password";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
