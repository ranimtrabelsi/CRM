package org.taskspfe.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.model.shop.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {


    @Query("select c from CartItem c where c.id = :id")
    Optional<CartItem> fetchCartItemWithId(@Param("id") final Long id);

    @Query(value = "select C from CartItem C where C.product.id = :productId")
    List<CartItem> fetchAllCartItemByProduct(@Param("productId") final long productId);

    @Modifying
    @Transactional
    @Query("delete from CartItem c where c.id = :id")
    void deleteCartItemById(final Long id);
}
