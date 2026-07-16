package com.bootcamp.springdatajpa.controller;

import com.bootcamp.springdatajpa.model.Product;
import com.bootcamp.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create -> JpaRepository.save()
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    // Read all -> JpaRepository.findAll()
    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    // Read one -> JpaRepository.findById()
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update -> JpaRepository.save() (same method handles insert or update)
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updated) {
        return productService.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setCategory(updated.getCategory());
                    existing.setPrice(updated.getPrice());
                    return ResponseEntity.ok(productService.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete -> JpaRepository.deleteById()
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = productService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Custom query methods
    @GetMapping("/category/{category}")
    public List<Product> byCategory(@PathVariable String category) {
        return productService.findByCategory(category);
    }

    @GetMapping("/cheaper-than/{price}")
    public List<Product> cheaperThan(@PathVariable Double price) {
        return productService.findCheaperThan(price);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return productService.search(keyword);
    }
}
