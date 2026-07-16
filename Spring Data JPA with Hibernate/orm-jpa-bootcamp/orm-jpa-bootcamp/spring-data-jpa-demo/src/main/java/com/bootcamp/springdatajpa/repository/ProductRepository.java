package com.bootcamp.springdatajpa.repository;

import com.bootcamp.springdatajpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This is a plain INTERFACE - no implementation is written by us at all.
 * Spring Data JPA generates the implementation at runtime, giving us:
 *
 *   - save(Product)          -> INSERT or UPDATE
 *   - findById(Long)         -> SELECT by primary key
 *   - findAll()              -> SELECT *
 *   - deleteById(Long)       -> DELETE by primary key
 *   - count(), existsById(), etc.
 *
 * for free, just by extending JpaRepository<Product, Long>.
 *
 * Below that, the "query methods" are parsed from their method names -
 * Spring Data JPA turns the method signature itself into a JPQL query,
 * no SQL/HQL/JPQL string required.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // SELECT p FROM Product p WHERE p.category = ?1
    List<Product> findByCategory(String category);

    // SELECT p FROM Product p WHERE p.price < ?1
    List<Product> findByPriceLessThan(Double price);

    // SELECT p FROM Product p WHERE p.name = ?1
    Optional<Product> findByName(String name);

    // SELECT p FROM Product p WHERE UPPER(p.name) LIKE UPPER('%?1%')
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // SELECT p FROM Product p WHERE p.category = ?1 ORDER BY p.price ASC
    List<Product> findByCategoryOrderByPriceAsc(String category);
}
