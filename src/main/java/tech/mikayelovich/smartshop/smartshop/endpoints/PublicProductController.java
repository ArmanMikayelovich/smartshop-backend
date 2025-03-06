package tech.mikayelovich.smartshop.smartshop.endpoints;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.mikayelovich.smartshop.smartshop.category.Category;
import tech.mikayelovich.smartshop.smartshop.category.CategoryService;
import tech.mikayelovich.smartshop.smartshop.endpoints.dto.CategoryResponse;
import tech.mikayelovich.smartshop.smartshop.endpoints.dto.ProductDetailsResponse;
import tech.mikayelovich.smartshop.smartshop.endpoints.dto.ProductResponse;
import tech.mikayelovich.smartshop.smartshop.endpoints.mapper.DtoMapper;
import tech.mikayelovich.smartshop.smartshop.product.Product;
import tech.mikayelovich.smartshop.smartshop.product.ProductService;

import static tech.mikayelovich.smartshop.smartshop.endpoints.mapper.DtoMapper.mapToCategoryResponses;
import static tech.mikayelovich.smartshop.smartshop.endpoints.mapper.DtoMapper.mapToDetailsResponse;
import static tech.mikayelovich.smartshop.smartshop.endpoints.mapper.DtoMapper.mapToProductResponses;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    // Constructor injection

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(
        @RequestParam(value = "onlyAvailable", required = false) Boolean onlyAvailable) {

        List<Product> products;
        if (Boolean.TRUE.equals(onlyAvailable)) {
            products = productService.getAllAvailableProducts();
        } else {
            products = productService.getAllProducts();
        }
        return ResponseEntity.ok(mapToProductResponses(products));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailsResponse> getProductDetails(@PathVariable Long id) {
        Product product = productService.getProductById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(mapToDetailsResponse(product));
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
        @RequestParam String q
    ) {
        List<Product> products = productService.searchProducts(q);
        return ResponseEntity.ok(
            products.stream()
                .map(DtoMapper::mapToProductResponse)
                .toList()
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(mapToCategoryResponses(categories));
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
        @PathVariable Long categoryId
    ) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(
            products.stream()
                .map(DtoMapper::mapToProductResponse)
                .toList()
        );
    }



}
