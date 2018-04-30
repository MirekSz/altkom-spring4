package pl.altkom.shop.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.text.WordUtils;

import pl.altkom.shop.model.FirstUpperLetter.FirstUpperLetterValidator;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstUpperLetterValidator.class)
public @interface FirstUpperLetter {
	String message() default "{firstUpperLetter.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public static class FirstUpperLetterValidator implements ConstraintValidator<FirstUpperLetter, String> {

		@Override
		public void initialize(FirstUpperLetter constraintAnnotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return WordUtils.capitalize(value).equals(value);
		}

	}
}
