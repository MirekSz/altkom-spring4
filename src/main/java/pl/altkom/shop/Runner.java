package pl.altkom.shop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.altkom.shop.repo.ProductRepo;
import pl.altkom.shop.service.ProductService;

public class Runner {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CoreConfig.class);
		ProductService productService = (ProductService) context.getBean("productService");
		System.out.println(productService);
		ProductRepo repo = context.getBean(ProductRepo.class);
		System.out.println(repo.getAll());
	}

}
