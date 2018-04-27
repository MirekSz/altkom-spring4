package pl.altkom.shop.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import pl.altkom.shop.model.Product;

@Repository
public class SpringDataProductRepoImpl implements SpringDataProductRepoCustom {
	@PersistenceContext
	EntityManager em;

	@Override
	public Product strange(Long id) {
		return em.find(Product.class, 1L);
	}

}
