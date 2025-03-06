package tech.mikayelovich.smartshop.smartshop;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import jakarta.annotation.Nonnull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SmartshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartshopApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nonnull CorsRegistry registry) {
                registry.addMapping("/api/public/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:4200")
                    .allowedMethods("GET", "POST")
                    .allowedHeaders("*")
                    .exposedHeaders("Content-Disposition")
                    .maxAge(3600);
            }
        };
    }

    @Bean
    public OpenAPI foodStoreOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                      .title("Food Store API")
                      .version("1.0")
                      .description("API for Online Food Store")
                      .contact(new Contact()
                                   .name("Support")
                                   .email("support@foodstore.com")));
    }

}
