package tech.mikayelovich.smartshop.smartshop.endpoints.mapper;

import java.util.List;
import java.util.stream.Collectors;
import tech.mikayelovich.smartshop.smartshop.category.Category;
import tech.mikayelovich.smartshop.smartshop.endpoints.dto.CategoryResponse;
import tech.mikayelovich.smartshop.smartshop.endpoints.dto.ProductDetailsResponse;
import tech.mikayelovich.smartshop.smartshop.endpoints.dto.ProductResponse;
import tech.mikayelovich.smartshop.smartshop.product.Product;
import tech.mikayelovich.smartshop.smartshop.product.image.Image;

public class DtoMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImages().isEmpty() ? null : product.getImages().get(0).getImageData(),
            product.getCategory() != null ? product.getCategory().getName() : null
        );
    }

    public static ProductDetailsResponse mapToDetailsResponse(Product product) {
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getImages().stream()
                .map(Image::getImageData)
                .collect(Collectors.toList()),
            product.getCategory() != null ? product.getCategory().getName() : null, product.isAvailable()
        );
        return productDetailsResponse;
    }

    public static CategoryResponse mapToCategoryResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName()
        );
    }

    public static List<ProductResponse> mapToProductResponses(List<Product> products) {
        return products.stream()
            .map(DtoMapper::mapToProductResponse)
            .collect(Collectors.toList());
    }

    public static List<CategoryResponse> mapToCategoryResponses(List<Category> categories) {
        return categories.stream()
            .map(DtoMapper::mapToCategoryResponse)
            .collect(Collectors.toList());
    }

}