package com.algonquin.cst8276.triggers.app.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algonquin.cst8276.triggers.app.model.Product;
import com.algonquin.cst8276.triggers.app.model.dto.ProductProjection;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> { // @formatter:off

	@Query("SELECT p.id AS id, p.name AS name, p.count AS count, p.unitPrice AS price"
			+ " FROM Product p"
			+ " WHERE p.id = ?1")
    ProductProjection findDtoById(Long productId);
	
	@Query("SELECT p.id AS id, p.name AS name, p.count AS count, p.unitPrice AS price"
	        + " FROM Product p")
	List<ProductProjection> findAllDtos();
	
	@Query("SELECT p.id AS id, p.name AS name, p.unitPrice AS price"
	        + " FROM Product p")
	List<ProductProjection> findAllNamesPrices();
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Product p"
	        + " SET p.count = p.count + :count"
	        + " WHERE  p.id = :id")
	void addInventory(@Param("id") Long productId, @Param("count") Integer newCount);
	
	@Query("SELECT p.count AS count"
	        + " FROM Product p"
	        + " WHERE p.id = ?1")
	Integer findCount(Long productId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Product p"
	        + " WHERE p.id = ?1")
	void deleteProduct(Long productId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO C##AL.PRODUCTS VALUES (C##AL.SEQ_PRODUCTS_ID.NEXTVAL, :name, :count, :price)", nativeQuery = true)
	void insertProduct(@Param("name") String name, @Param("count") Integer count, @Param("price") BigDecimal unitPrice);

} // @formatter:on
