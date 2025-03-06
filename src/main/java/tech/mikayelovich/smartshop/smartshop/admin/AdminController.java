package tech.mikayelovich.smartshop.smartshop.admin;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tech.mikayelovich.smartshop.smartshop.category.Category;
import tech.mikayelovich.smartshop.smartshop.category.CategoryService;
import tech.mikayelovich.smartshop.smartshop.product.Product;
import tech.mikayelovich.smartshop.smartshop.product.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // ================== CATEGORY MANAGEMENT ==================
    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/add-category";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/add-category";
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    // ================== EDIT CATEGORY ==================
    @GetMapping("/categories/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isEmpty()) {
            return "redirect:/admin/categories"; // Handle not found
        }
        model.addAttribute("category", category.get());
        return "admin/edit-category";
    }

    @PostMapping("/categories/edit/{id}")
    public String updateCategory(
        @PathVariable Long id,
        @ModelAttribute("category") @Valid Category updatedCategory,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "admin/edit-category";
        }
        categoryService.saveCategory(updatedCategory);
        return "redirect:/admin/categories";
    }


    // ================== PRODUCT MANAGEMENT ==================
    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/add-product";
    }

    @PostMapping("/products/add")
    public String addProduct(
        @ModelAttribute("product") @Valid Product product,
        BindingResult result,
        @RequestParam("imageFiles") MultipartFile[] imageFiles,
        Model model
    ) throws IOException {
        model.addAttribute("categories", categoryService.getAllCategories());

        if (result.hasErrors()) {
            return "admin/add-product";
        }

        if (imageFiles.length == 0 || imageFiles[0].isEmpty()) {
            model.addAttribute("imageError", "At least one image is required");
            return "admin/add-product";
        }

        productService.saveProduct(product, imageFiles);
        return "redirect:/admin/products";
    }

    // ================== EDIT PRODUCT ==================
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/edit-product";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(
        @PathVariable Long id,
        @ModelAttribute("product") @Valid Product updatedProduct,
        BindingResult result,
        @RequestParam("imageFiles") MultipartFile[] imageFiles,
        Model model
    ) throws IOException {
        model.addAttribute("categories", categoryService.getAllCategories());

        if (result.hasErrors()) {
            return "admin/edit-product";
        }

        productService.saveProduct(updatedProduct, imageFiles);
        return "redirect:/admin/products";
    }

    @GetMapping
    public String adminHome() {
        return "admin/home"; // Maps to templates/admin/home.html
    }
}