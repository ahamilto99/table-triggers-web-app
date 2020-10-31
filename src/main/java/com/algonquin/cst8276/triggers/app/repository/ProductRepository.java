package com.algonquin.cst8276.triggers.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    ProductProjection findProjectionById(Long productId);

} // @formatter:on
