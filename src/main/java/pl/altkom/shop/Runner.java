package pl.altkom.shop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.altkom.shop.service.ProductService;

public class Runner {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(CoreConfig.class);
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("config.xml");
		ProductService productService = (ProductService) context.getBean("productService");
		System.out.println(productService);
	}

}
