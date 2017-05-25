package pl.altkom.shop.controller;

import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	public void hello(Writer writer) throws Exception {
		writer.write("Hello World");
	}
}
