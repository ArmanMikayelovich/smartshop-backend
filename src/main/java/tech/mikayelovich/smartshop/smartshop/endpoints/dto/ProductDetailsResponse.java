package tech.mikayelovich.smartshop.smartshop.endpoints.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProductDetailsResponse {

    private Long id;
    private String name;
    private String description; // HTML content
    private Double price;
    private List<String> images; // Base64 encoded
    private String categoryName;
    private boolean available;

    // Constructor
    public ProductDetailsResponse(
        Long id,
        String name,
        String description,
        Double price,
        List<String> images,
        String categoryName,
        boolean available
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.categoryName = categoryName;
        this.available = available;
    }

}