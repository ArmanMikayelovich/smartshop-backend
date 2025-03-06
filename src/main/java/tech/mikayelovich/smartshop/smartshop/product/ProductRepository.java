package tech.mikayelovich.smartshop.smartshop.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByAvailableTrueOrderByNameAsc();

    @Query("SELECT p FROM Product p " +
           "WHERE p.category.id = :categoryId " +
           "AND p.available = true " +
           "ORDER BY p.name ASC")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p " +
           "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "AND p.available = true " +
           "ORDER BY p.name ASC")
    List<Product> fullTextSearch(@Param("query") String query);

}
