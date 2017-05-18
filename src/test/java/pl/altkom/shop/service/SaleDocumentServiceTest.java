package pl.altkom.shop.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
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
import pl.altkom.shop.repo.ProductRepo;
import pl.altkom.shop.repo.SaleDocumentInfo;
import pl.altkom.shop.repo.SaleDocumentRepo;
import pl.altkom.shop.repo.SaleDocumentSearcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfig.class)
@Rollback
@Transactional
@ActiveProfiles("test")
@WebAppConfiguration
public class SaleDocumentServiceTest {

	@Inject
	ProductRepo repo;
	@Inject
	SaleDocumentRepo saleDocumentRepo;

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
		List<SaleDocumentInfo> findSaleDocument = saleDocumentRepo.findSaleDocument(saleDocumentSearcher);

		// then
		assertThat(findSaleDocument).isEmpty();
	}

	@Test
	public void shoulFindSaleDocumentsByQueryDSL() throws Exception {
		// given

		// when
		Iterable<SaleDocument> findSaleDocument = saleDocumentRepo.findAll(QSaleDocument.saleDocument.id.isNotNull(),
				new PageRequest(1, 10));

		// then
		assertThat(findSaleDocument).isNotEmpty();
		assertThat(findSaleDocument.iterator().next()).isInstanceOf(SaleDocument.class);
	}
}
