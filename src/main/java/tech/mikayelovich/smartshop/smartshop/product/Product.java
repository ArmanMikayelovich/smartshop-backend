package tech.mikayelovich.smartshop.smartshop.product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.mikayelovich.smartshop.smartshop.category.Category;
import tech.mikayelovich.smartshop.smartshop.product.image.Image;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @Positive(message = "Price must be positive")
    private Double price;

    @Column(nullable = false)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity; // New field

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>();



    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Encodes HTML to Base64 before saving to the database.
     */
    @PrePersist
    @PreUpdate
    public void encodeDescription() {
        if (this.description != null && !this.description.isEmpty()) {
            this.description = Base64.getEncoder().encodeToString(this.description.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Decodes Base64 to HTML after loading from the database.
     */
    @PostLoad
    public void decodeDescription() {
        if (this.description != null && !this.description.isEmpty()) {
            this.description = new String(Base64.getDecoder().decode(this.description), StandardCharsets.UTF_8);
        }
    }
}