package pl.altkom.shop.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.altkom.shop.model.Product;

@Repository
@Transactional
public class HibernateProductRepo implements ProductRepo {
	@PersistenceContext
	EntityManager em;

	@Override
	public Long insert(Product product) {
		em.persist(product);
		return product.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return (Long) em.createQuery("SELECT count(*) FROM Product p").getSingleResult();
	}

	@Override
	public void delete(Long id) {
		Product product = em.find(Product.class, id);
		em.remove(product);
	}

	@Override
	@Transactional(readOnly = true)
	public Product find(Long id) {
		Product product = em.find(Product.class, id);
		return product;
	}

	@Override
	public void update(Product product) {
		em.merge(product);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAll(@NotNull String name) {
		// Preconditions.checkNotNull(name, "query is null");
		Query query = em.createQuery("From Product p WHERE p.name LIKE :name");
		query.setParameter("name", name + "%");
		// return em.createQuery("FROM Product p").getResultList();
		List resultlist = query.getResultList();
		return resultlist;
	}
}
