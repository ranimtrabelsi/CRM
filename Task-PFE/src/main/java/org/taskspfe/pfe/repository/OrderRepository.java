package org.taskspfe.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.model.shop.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order>  fetchOrderById(Integer id);


    @Query(value = "SELECT o FROM Order o WHERE o.user.id = :id")
    List<Order> fetchOrderOfUserById(UUID id);

    @Query(value = "SELECT o FROM Order o")
    List<Order> fetchAllOrders();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Order o WHERE o.user.id = :id")
    void deleteByUserId(Integer id);
}
