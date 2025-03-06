package tech.mikayelovich.smartshop.smartshop.product.image;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.mikayelovich.smartshop.smartshop.product.Product;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteAllByProduct(Product product);
}
