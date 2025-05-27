package org.taskspfe.pfe.controller.user;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.taskspfe.pfe.dto.shop.CartDTO;
import org.taskspfe.pfe.dto.shop.CartItemDTO;
import org.taskspfe.pfe.dto.shop.OrderDTO;
import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.service.email.EmailSenderService;
import org.taskspfe.pfe.service.user.UserEntityService;
import org.taskspfe.pfe.utility.CustomResponseEntity;
import org.taskspfe.pfe.utility.CustomResponseList;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RequestMapping("api/v1/users")
@RestController
public class UserEntityController {

    private final UserEntityService userEntityService;
    private final EmailSenderService emailSenderService;

    public UserEntityController(UserEntityService userEntityService, EmailSenderService emailSenderService) {
        this.userEntityService = userEntityService;
        this.emailSenderService = emailSenderService;
    }


    @GetMapping("/admin/get/id/{userId}")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(@PathVariable("userId") final UUID userId) {
        return userEntityService.fetchUserById(userId);
    }

    @GetMapping("/admin/get/all/users")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllUsers() {
        return userEntityService.fetchAllUsers();
    }

    @GetMapping("/all/get/current_user")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userEntityService.fetchCurrentUser(userDetails);
    }

    @GetMapping("/all/admins")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllAdmins() {
        return userEntityService.fetchAllAdmins();
    }

    @GetMapping("/all/clients")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllClients() {
        return userEntityService.fetchAllClients();
    }

    @PutMapping("/admin/enable/{userId}")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> enableUser(@PathVariable("userId") final UUID userId) {
        return userEntityService.enableUser(userId);
    }

    @GetMapping("/admin/count/clients/{year}")
    public ResponseEntity<CustomResponseEntity<Map<String, Long>>> countClientsByYear(@PathVariable("year") final int year) {
        return userEntityService.countClientsByYear(year);
    }

    @PutMapping("/admin/disable/{userId}")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> disableUser(@PathVariable("userId") final UUID userId) {
        return userEntityService.disableUser(userId);
    }

    @GetMapping("/admin/order")
    public ResponseEntity<CustomResponseEntity<List<OrderDTO>>> fetchAllOrders() {
        return userEntityService.fetchAllOrders();
    }


    @GetMapping("/all/technicians")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllTechnicians() {
        return userEntityService.fetchAllTechnicians();
    }

    @PutMapping("/all/update")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> updateUser(
            @AuthenticationPrincipal @NotNull UserDetails userDetails,
            @RequestBody final UserEntity userEntity
    ) {
        return userEntityService.updateUser(userDetails, userEntity);
    }


    @GetMapping("/all/reclamation")
    public ResponseEntity<CustomResponseEntity<String>> sendMail(
            @RequestParam(name = "subject", required = true) String subject,
            @RequestParam(name = "description", required = true) String description) {
        emailSenderService.sendEmail("trabelsiranim6@gmail.com", subject, emailSenderService.emailTemplateContact(subject, description));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK, "Email sent successfully"));
    }


    @PostMapping("/all/add-basket")
    public ResponseEntity<CustomResponseEntity<String>> addItemToBasket(
            @AuthenticationPrincipal @NotNull UserDetails userDetails,
            @RequestBody @NotNull CartDTO cartDTO
    ) {
        return userEntityService.addItemToBasket(userDetails, cartDTO);
    }

    @DeleteMapping("/all/remove-basket/{cartItemId}")
    public ResponseEntity<CustomResponseEntity<String>> removeItemToBasket(
            @AuthenticationPrincipal @NotNull UserDetails userDetails,
            @PathVariable("cartItemId") @NotNull Long cartItemId
    ) {
        return userEntityService.removeItemToBasket(userDetails, cartItemId);
    }

    @PutMapping("/all/update-basket/{cartItemId}")
    public ResponseEntity<CustomResponseEntity<String>> updateItemToBasket(
            @AuthenticationPrincipal @NotNull UserDetails userDetails,
            @PathVariable("cartItemId") @NotNull Long cartItemId,
            @RequestParam(name = "newQuantity", required = true) int newQuantity
    ) {
        return userEntityService.updateItemToBasket(userDetails, cartItemId, newQuantity);
    }

    @PostMapping("/all/checkout")
    public ResponseEntity<CustomResponseEntity<OrderDTO>> checkout(
            @AuthenticationPrincipal @NotNull UserDetails userDetails) {
        return userEntityService.checkout(userDetails);
    }

    @GetMapping("/all/cart-items")
    public ResponseEntity<CustomResponseEntity<List<CartItemDTO>>> fetchCartItems(@AuthenticationPrincipal @NotNull UserDetails userDetails) {
        return userEntityService.fetchCartItems(userDetails);
    }

    @GetMapping("/all/orders")
    public ResponseEntity<CustomResponseEntity<List<OrderDTO>>> fetchAllOrdersByUser(@AuthenticationPrincipal@NotNull UserDetails userDetails) {
        return userEntityService.fetchAllOrdersByUser(userDetails);
    }

}