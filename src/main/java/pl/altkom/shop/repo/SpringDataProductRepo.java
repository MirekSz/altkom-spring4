package pl.altkom.shop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.altkom.shop.model.Product;

@Repository
public interface SpringDataProductRepo extends JpaRepository<Product, Long>, SpringDataProductRepoCustom {
	@Query("FROM Product")
	List<Product> getAll();

	@Query("FROM Product where id = :id")
	Product find(@Param("id") Long id);

}
