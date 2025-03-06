package tech.mikayelovich.smartshop.smartshop.endpoints.dto;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String name,
    Double price,
    String mainImage, // Base64 of first image
    String categoryName
) {}