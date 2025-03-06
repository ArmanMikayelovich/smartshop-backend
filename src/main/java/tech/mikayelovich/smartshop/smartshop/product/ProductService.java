package tech.mikayelovich.smartshop.smartshop.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.mikayelovich.smartshop.smartshop.category.CategoryService;
import tech.mikayelovich.smartshop.smartshop.product.image.Image;
import tech.mikayelovich.smartshop.smartshop.product.image.ImageRepository;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ImageRepository imageRepository;

    @Transactional
    public void saveProduct(Product product, MultipartFile[] imageFiles) throws IOException {
        // Delete existing images if editing
        if (product.getId() != null) {
            imageRepository.deleteAllByProduct(product);
        }

        // Process new images
        List<Image> images = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                Image image = new Image();
                image.setImageData(Base64.getEncoder().encodeToString(imageFile.getBytes()));
                image.setProduct(product);
                images.add(image);
            }
        }

        product.setImages(images);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}