package org.taskspfe.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.taskspfe.pfe.model.product.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Integer> {


    @Query(value = "select p from Product p where p.id = :id")
    Optional<Product> fetchProductById(@Param("id") long id);

}
