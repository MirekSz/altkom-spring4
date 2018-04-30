package pl.altkom.shop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.altkom.shop.lib.ProductChangeEvent;
import pl.altkom.shop.service.ProductService;

public class Runner {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CoreConfig.class);
		ProductService productService = (ProductService) context.getBean("productService");
		System.out.println(productService);
		context.publishEvent(new ProductChangeEvent());
	}

}
