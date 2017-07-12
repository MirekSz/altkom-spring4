
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.altkom.shop.CoreConfig;
import pl.altkom.shop.model.Product;
import pl.altkom.shop.repo.ProductRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfig.class)
@Rollback
@Transactional
@ActiveProfiles("test")
public class LucieneTest {
	@Inject
	ProductRepo repo;
	@PersistenceContext
	EntityManager em;

	@Test
	public void shoulAddProduct() throws Exception {
		// given
		Product product = new Product("rower", "2", 11, BigDecimal.TEN);
		int beforeInsertSize = repo.getAll().size();

		// when
		repo.insert(product);

		// then
		assertThat(repo.getAll().size()).isGreaterThan(beforeInsertSize);
	}

	@Test
	public void shoulFindProductByIndex() throws Exception {
		// given
		String numberPart = "2078";
		String description = "FAS/123/" + numberPart + " The Art of Computer Science Europe/Berlin GSX-R1000";
		String name = "rower";
		Product product = new Product(name, description, 11, BigDecimal.TEN);
		int beforeInsertSize = repo.getAll().size();
		FullTextEntityManager fullTextSession = Search.getFullTextEntityManager(em);
		fullTextSession.createIndexer().startAndWait();

		// when
		repo.insert(product);

		em.flush(); // <-- ADDED FLUSH() CALL
		fullTextSession.flushToIndexes();

		// then
		assertThat(repo.getAll().size()).isGreaterThan(beforeInsertSize);
		em.clear();
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();

		final BooleanJunction<BooleanJunction> outer = qb.bool();
		outer.should(qb.keyword().onFields("name").matching(numberPart).createQuery());
		outer.should(qb.keyword().onFields("description").matching(numberPart).createQuery());

		// wrap Lucene query in a org.hibernate.Query
		FullTextQuery query2 = fullTextSession.createFullTextQuery(outer.createQuery(), Product.class);

		// execute search
		List<Product> result = query2.getResultList();
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getName()).isEqualTo(name);
		fullTextSession.purgeAll(Product.class);

	}

	@Test
	public void shoulFindProductByIndexWithProjection() throws Exception {
		// given
		String numberPart = "2078";
		String description = "FAS/123/" + numberPart + " The Art of Computer Science Europe/Berlin GSX-R1000";
		String name = "rower";
		Product product = new Product(name, description, 11, BigDecimal.TEN);
		int beforeInsertSize = repo.getAll().size();
		FullTextEntityManager fullTextSession = Search.getFullTextEntityManager(em);
		fullTextSession.createIndexer().startAndWait();

		// when
		repo.insert(product);

		em.flush(); // <-- ADDED FLUSH() CALL
		fullTextSession.flushToIndexes();

		// then
		assertThat(repo.getAll().size()).isGreaterThan(beforeInsertSize);
		em.clear();
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();

		final BooleanJunction<BooleanJunction> outer = qb.bool();
		outer.should(qb.keyword().onFields("name").matching(numberPart).createQuery());
		outer.should(qb.keyword().onFields("description").matching(numberPart).createQuery());

		// wrap Lucene query in a org.hibernate.Query
		FullTextQuery query2 = fullTextSession.createFullTextQuery(outer.createQuery(), Product.class);
		query2.setProjection("id", "name");

		// execute search
		List<Object[]> result = query2.getResultList();
		assertThat(result).hasSize(1);
		assertThat(result.get(0)[1]).isEqualTo(name);
		fullTextSession.purgeAll(Product.class);

	}

}
