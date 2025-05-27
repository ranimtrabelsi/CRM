package org.taskspfe.pfe.service.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.taskspfe.pfe.dto.shop.CartDTO;
import org.taskspfe.pfe.dto.shop.CartItemDTO;
import org.taskspfe.pfe.dto.shop.OrderDTO;
import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.model.shop.CartItem;
import org.taskspfe.pfe.model.shop.Order;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.utility.CustomResponseEntity;
import org.taskspfe.pfe.utility.CustomResponseList;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserEntityService {

    ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllAdmins();
    ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllClients();
    ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllTechnicians();
    ResponseEntity<CustomResponseEntity<UserEntityDTO>> enableUser(final UUID userId);
    ResponseEntity<CustomResponseEntity<UserEntityDTO>> disableUser(final UUID userId);
    ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(final UUID userId);
    ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllUsers();
    ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(final UserDetails userDetails);
    ResponseEntity<CustomResponseEntity<Map<String, Long>>> countClientsByYear(int year);
    ResponseEntity<CustomResponseEntity<UserEntityDTO>> updateUser(@NotNull UserDetails userDetails,final UserEntity userEntity);

    ResponseEntity<CustomResponseEntity<String>> addItemToBasket(@NotNull UserDetails userDetails, @NotNull CartDTO cartDTO);
    ResponseEntity<CustomResponseEntity<String>> removeItemToBasket(@NotNull UserDetails userDetails, @NotNull Long cartItemId);
    ResponseEntity<CustomResponseEntity<String>> updateItemToBasket(@NotNull UserDetails userDetails, @NotNull Long cartItemId, int newQuantity);
    ResponseEntity<CustomResponseEntity<OrderDTO>> checkout(@NotNull UserDetails userDetails);
    ResponseEntity<CustomResponseEntity<List<CartItemDTO>>> fetchCartItems(@NotNull UserDetails userDetails);
    ResponseEntity<CustomResponseEntity<List<OrderDTO>>> fetchAllOrders();
    ResponseEntity<CustomResponseEntity<List<OrderDTO>>> fetchAllOrdersByUser(@NotNull UserDetails userDetails);
    UserEntity getUserEntityById(final UUID userId);
    UserEntity getUserEntityByEmail(final String email);
    boolean isEmailRegistered(final String email);
    UserEntity saveUser(@NotNull final UserEntity userEntity);

}
