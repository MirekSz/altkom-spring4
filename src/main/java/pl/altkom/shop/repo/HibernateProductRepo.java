package pl.altkom.shop.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import pl.altkom.shop.model.Product;

//@Repository
public class HibernateProductRepo implements ProductRepo {
	@PersistenceUnit
	EntityManagerFactory emf;

	private EntityManager startTx() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		return em;
	}

	private void commit(EntityManager em) {
		EntityTransaction transaction = em.getTransaction();
		transaction.commit();
	}

	@Override
	public Long insert(Product product) {
		EntityManager em = startTx();
		em.persist(product);
		commit(em);
		return product.getId();
	}

	@Override
	public Long count() {
		EntityManager em = startTx();
		return (Long) em.createQuery("SELECT count(*) FROM Product p").getSingleResult();
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public Product find(Long id) {
		return null;
	}

	@Override
	public void update(Product product) {

	}

	@Override
	public List<Product> getAll() {
		return Collections.EMPTY_LIST;
	}

}
