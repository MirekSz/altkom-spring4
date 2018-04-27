package pl.altkom.shop.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pl.altkom.shop.model.Product;

//@Repository
public class JDBCProductRepo implements ProductRepo {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("name"),
			rs.getString("description"), rs.getInt("quantity"), rs.getBigDecimal("price"));

	@Override
	public Long insert(Product product) {
		Long id = jdbcTemplate.queryForObject("select max(id) from product", Long.class) + 1;
		jdbcTemplate.update("insert into product (id, name,description,quantity,price) VALUES (?,?,?,?,?)", id,
				product.getName(), product.getDescription(), product.getQuantity(), product.getPrice());
		return id;
	}

	@Override
	public Long count() {
		return jdbcTemplate.queryForObject("select count(*) from product", Long.class);
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from product where id=?", id);
	}

	@Override
	public Product find(Long id) {
		return jdbcTemplate.queryForObject("select * from product where id=?", new Object[] { id }, productRowMapper);
	}

	@Override
	public void update(Product product) {
		jdbcTemplate.update("update product set name=?,description=?,quantity=?,price=? where id = ?",
				product.getName(), product.getDescription(), product.getQuantity(), product.getPrice(),
				product.getId());

	}

	@Override
	public List<Product> getAll() {
		return jdbcTemplate.query("select * from product", productRowMapper);
	}

}
