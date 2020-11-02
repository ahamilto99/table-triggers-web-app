package com.algonquin.cst8276.triggers.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algonquin.cst8276.triggers.app.model.AuditProduct;
import com.algonquin.cst8276.triggers.app.model.dto.AuditProductProjection;

@Repository
@Transactional(readOnly = true)
public interface AuditProductRepository extends JpaRepository<AuditProduct, Long> { // @formatter:off
    
    AuditProduct findByProductId(Long productId);

    @Query("SELECT COUNT(ap.revisionType)"
            + " FROM AuditProduct ap"
            + " WHERE ap.revisionType = 'INSERT'")
    long findNumInserts();
    
    @Query("SELECT COUNT(ap.revisionType)"
            + " FROM AuditProduct ap"
            + " WHERE ap.revisionType = 'DELETE'")
    long findNumDeletes();
    
    @Query("SELECT COUNT(ap.revisionType)"
            + " FROM AuditProduct ap"
            + " WHERE ap.revisionType = 'UPDATE'")
    long findNumUpdates();

    @Query(value = "SELECT id AS id, name AS name, inventory_count AS count, inventory_change AS countChange, revision_type AS revType,"
                    + "     unit_price AS price, revision_tstmp AS revTstmp, product_id AS productId"
                    + " FROM audit_products"
                    + " WHERE product_id = ?1"
                    + " ORDER BY revision_tstmp DESC"
                    + " FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    AuditProductProjection findMostRecentRevision(Long productId);
    
    @Query("SELECT CASE WHEN (COUNT(ap.id) = 1) THEN TRUE"
            + " ELSE FALSE END"
            + " FROM AuditProduct ap "
            + " WHERE ap.revisionType = 'DELETE'"
            + " AND ap.productId = ?1")
    Boolean checkIfDeleted(Long productId);
    
    @Query("SELECT ap.name AS name, ap.unitPrice AS price, ap. count AS count, ap.inventoryChange AS countChange,"
                + " ap.revisionType AS revType, ap.revisionTstmp AS revTstmp"
            + " FROM AuditProduct ap"
            + " ORDER BY ap.id DESC")
    List<AuditProductProjection> findAllDtos(); 
    
}   // @formatter:on
