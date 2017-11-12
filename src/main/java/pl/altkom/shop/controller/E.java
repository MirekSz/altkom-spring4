package pl.altkom.shop.controller;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class E {
	@ExceptionHandler
	@ResponseBody
	public List<ObjectError> handle(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		return bindingResult.getAllErrors();
	}
}
