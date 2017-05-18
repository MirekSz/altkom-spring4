package pl.altkom.shop.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.JPQLQuery;

import pl.altkom.shop.model.QProduct;
import pl.altkom.shop.model.QSaleDocument;
import pl.altkom.shop.model.QSaleDocumentItem;
import pl.altkom.shop.model.SaleDocument;

@Repository
@Transactional
public class SaleDocumentRepoImpl extends QueryDslRepositorySupport implements SaleDocumentRepoCustom {
	public SaleDocumentRepoImpl() {
		super(SaleDocument.class);
	}

	@PersistenceContext
	EntityManager em;

	@Override
	public SaleDocument strange(Long id) {
		return em.find(SaleDocument.class, 13L);
	}

	@Override
	public List<SaleDocumentInfo> findSaleDocument(SaleDocumentSearcher search) {
		QSaleDocument saledocument = QSaleDocument.saleDocument;
		QSaleDocumentItem saleDocumentItem = QSaleDocumentItem.saleDocumentItem;
		QProduct product = QProduct.product;

		JPQLQuery query = from(saledocument);
		query.innerJoin(saledocument.items, saleDocumentItem);
		query.innerJoin(saleDocumentItem.product, product);

		if (search.where != null) {
			query.where(search.where);
		}
		if (search.orderBy != null) {
			query.orderBy(search.orderBy);
		}
		if (search.page != null) {
			query.offset(search.page * search.pageSize).limit(search.pageSize);
		}
		return query.list(new QSaleDocumentInfo(saledocument.no, saleDocumentItem.id, product.name));
	}

}
