package pl.altkom.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	public class GeneralError {
		private String message;

		public GeneralError(String message) {
			this.setMessage(message);
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

	@ExceptionHandler
	@ResponseBody
	public ValidationErrorDTO handle(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		return processFieldErrors(bindingResult.getFieldErrors());
	}

	@ExceptionHandler
	@ResponseBody
	public GeneralError handle(Exception e) {
		return new GeneralError(e.getMessage());
	}

	private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
		ValidationErrorDTO dto = new ValidationErrorDTO();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			dto.addFieldError(fieldError.getField(), localizedErrorMessage);
		}

		return dto;
	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
			String[] fieldErrorCodes = fieldError.getCodes();
			localizedErrorMessage = fieldErrorCodes[0];
		}

		return localizedErrorMessage;
	}

	public class FieldErrorDTO {

		private String field;
		private String message;

		public FieldErrorDTO(String field, String message) {
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

	public class ValidationErrorDTO {

		private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

		public ValidationErrorDTO() {

		}

		public void addFieldError(String path, String message) {
			FieldErrorDTO error = new FieldErrorDTO(path, message);
			getFieldErrors().add(error);
		}

		public List<FieldErrorDTO> getFieldErrors() {
			return fieldErrors;
		}

		public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
			this.fieldErrors = fieldErrors;
		}

	}
}
