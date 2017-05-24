package pl.altkom.shop.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExHandler {
	@ExceptionHandler(Exception.class)
	public String handleError(Exception e, Model model) {
		model.addAttribute("exception", e);
		return "error";
	}
}
