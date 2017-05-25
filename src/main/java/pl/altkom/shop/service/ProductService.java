package pl.altkom.shop.service;

import org.springframework.beans.factory.annotation.Autowired;

import pl.altkom.shop.repo.ProductRepo;

public class ProductService {
	@Autowired
	private ProductRepo repo;
}
