package pl.altkom.shop.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import pl.altkom.shop.model.Product;

//@Repository
public class InMemoryProductRepo implements ProductRepo {
	Logger log = Logger.getLogger(InMemoryProductRepo.class);

	Map<Long, Product> products = new HashMap<Long, Product>();

	@PostConstruct
	public void init() {
		insert(new Product("Rower", "Bardzo dobry rower", 12, BigDecimal.TEN));
		insert(new Product("Sanki", "Sanki zimowe", 123, BigDecimal.valueOf(12.45)));

		log.info("#####################################");
		log.info("Products added");
		log.info("#####################################");
	}

	@Override
	public Long insert(Product product) {
		product.setId(products.size() + 1L);

		products.put(product.getId(), product);

		return product.getId();
	}

	@Override
	public Long count() {
		return (long) products.size();
	}

	@Override
	public List<Product> getAll() {
		return new ArrayList<Product>(products.values());
	}

	@Override
	public void delete(Long id) {
		this.products.remove(id);

	}

	@Override
	public Product find(Long id) {
		return products.get(id);
	}

	@Override
	public void update(Product product) {
		products.put(product.getId(), product);

	}
}
