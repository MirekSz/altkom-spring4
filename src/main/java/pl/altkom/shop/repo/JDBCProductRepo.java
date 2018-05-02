package pl.altkom.shop.repo;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import pl.altkom.shop.model.Product;

//@Repository
public class JDBCProductRepo implements ProductRepo {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Long insert(Product product) {
		Long id = jdbcTemplate.queryForObject("select max(id) from product", Long.class) + 1;
		jdbcTemplate.update(
				"insert into product (id, name,description,quantity,price,imgLocation) VALUES (?,?,?,?,?,?)", id,
				product.getName(), product.getDescription(), product.getQuantity(), product.getPrice(),
				product.getImgLocation());
		return id;
	}

	@Override
	public Long count() {
		return jdbcTemplate.queryForObject("select count(*) from product", Long.class);
	}

	@Override
	public List<Product> getAll() {
		return Collections.EMPTY_LIST;
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

}
