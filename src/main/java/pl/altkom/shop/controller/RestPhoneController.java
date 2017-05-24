package pl.altkom.shop.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.altkom.shop.model.Product;
import pl.altkom.shop.repo.ProductRepo;

//@RestController
//@RequestMapping("api/products")

public class RestPhoneController {
	@Inject
	ProductRepo repo;

	@RequestMapping(method = RequestMethod.GET)
	public List<Product> list() {
		return repo.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Product insert(@RequestBody Product product) {
		repo.insert(product);
		return product;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		repo.delete(id);

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Product> save(@RequestBody Product product) {
		Long id = repo.insert(product);
		Product saveProduct = repo.find(id);
		return new ResponseEntity<Product>(saveProduct, HttpStatus.CREATED);

	}

}
