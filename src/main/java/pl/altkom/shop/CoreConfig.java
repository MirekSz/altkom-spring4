package pl.altkom.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import pl.altkom.shop.service.ProductService;

@ComponentScan("pl.altkom.shop")
public class CoreConfig {
	@Bean
	public ProductService productService() {
		return new ProductService();
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
