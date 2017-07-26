package pl.altkom.shop.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import pl.altkom.shop.CoreConfig;
import pl.altkom.shop.model.Product;
import pl.altkom.shop.model.QProduct;
import pl.altkom.shop.model.QSaleDocument;
import pl.altkom.shop.model.SaleDocument;
import pl.altkom.shop.model.SaleDocumentItem;
import pl.altkom.shop.repo.ProductRepo;
import pl.altkom.shop.repo.SaleDocumentInfo;
import pl.altkom.shop.repo.SaleDocumentRepo;
import pl.altkom.shop.repo.SaleDocumentSearcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfig.class)
@Transactional
@Rollback
@ActiveProfiles("test")
@WebAppConfiguration
public class SaleDocumentServiceTest {

	@Inject
	ProductRepo repo;
	@Inject
	SaleDocumentRepo saleDocumentRepo;
	@Inject
	pl.altkom.shop.mrepo.ProductRepo productRepo;
	@Inject
	pl.altkom.shop.mrepo.SaleDocumentItemRepo saleDocumentItemRepo;

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
	public void shoulFindSaleDocumentByNumber() throws Exception {
		// given

		// when
		SaleDocument findByNumber = saleDocumentRepo.findByNo(1L);

		// then
		assertThat(findByNumber).isNotNull();
	}

	@Test
	public void shoulFindSaleDocumentsInfo() throws Exception {
		// given

		// when
		SaleDocumentSearcher saleDocumentSearcher = new SaleDocumentSearcher();
		saleDocumentSearcher.where = QProduct.product.name.startsWithIgnoreCase("mirek");
		saleDocumentSearcher.orderBy = QSaleDocument.saleDocument.no.desc();
		saleDocumentRepo.findSaleDocument(saleDocumentSearcher);
		Iterable<SaleDocument> findSaleDocument = saleDocumentRepo.findAll(QSaleDocument.saleDocument.no.eq(1L),
				QSaleDocument.saleDocument.no.desc());

		// then
		assertThat(findSaleDocument).isEmpty();
	}

	@Test
	public void shoulFindSaleDocumentsByQueryDSL() throws Exception {
		// given

		// when
		Iterable<SaleDocument> findSaleDocument = saleDocumentRepo.findAll(QSaleDocument.saleDocument.id.isNotNull(),
				new PageRequest(1, 10, new QSort(QSaleDocument.saleDocument.no.asc())));

		// then
		assertThat(findSaleDocument).isNotEmpty();
		assertThat(findSaleDocument.iterator().next()).isInstanceOf(SaleDocument.class);
	}

	@Test
	public void shoulFindSaleDocumentsByGUI() throws Exception {
		// given

		// when
		Map<String, Object> where = new HashMap<>();
		where.put("no", 12L);
		where.put("saleDocumentItem.lp", 12);

		Map<String, Integer> order = new HashMap<>();
		order.put("no", 1);
		order.put("saleDocumentItem.lp", 0);
		List<SaleDocumentInfo> findSaleDocument = saleDocumentRepo.findByGUI(where, order);

		// then
		// assertThat(findSaleDocument).isNotEmpty();
	}

	@Test
	public void shoulFindAllProductsByMyBiats() throws Exception {
		// when
		List<Product> findAll = productRepo.findAll();
		int size = findAll.size();
		Product product = new Product();
		product.setName("a");
		product.setPrice(BigDecimal.TEN);
		product.setQuantity(10);
		productRepo.save(product);

		// then
		findAll = productRepo.findAll();
		assertThat(findAll.size()).isGreaterThan(size);
	}

	@Test
	public void shoulFindSaleDocumentItemsWithProductByMyBiats() throws Exception {
		// when
		List<SaleDocumentItem> findAll = saleDocumentItemRepo.findAll();

		// then
		assertThat(findAll.get(0).getProduct()).isNotNull();
		assertThat(findAll.get(0).getProduct().getName()).isNotNull();
	}
}
