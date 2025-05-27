package org.taskspfe.pfe.service.user;


import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.shop.*;
import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.dto.user.UserEntityDTOMapper;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.exceptions.UnauthorizedActionException;
import org.taskspfe.pfe.model.product.Product;
import org.taskspfe.pfe.model.shop.CartItem;
import org.taskspfe.pfe.model.shop.Order;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.repository.CartItemRepository;
import org.taskspfe.pfe.repository.OrderRepository;
import org.taskspfe.pfe.repository.UserEntityRepository;
import org.taskspfe.pfe.service.product.ProductService;
import org.taskspfe.pfe.utility.CustomResponseEntity;
import org.taskspfe.pfe.utility.CustomResponseList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final UserEntityDTOMapper userEntityDTOMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemDTOMapper cartItemDTOMapper;
    private final OrderRepository orderRepository;
    private final OrderDTOMapper orderDTOMapper;
    private final ProductService productService;


    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, UserEntityDTOMapper userEntityDTOMapper, CartItemRepository cartItemRepository, CartItemDTOMapper cartItemDTOMapper, OrderRepository orderRepository, OrderDTOMapper orderDTOMapper, ProductService productService) {
        this.userEntityRepository = userEntityRepository;
        this.userEntityDTOMapper = userEntityDTOMapper;
        this.cartItemRepository = cartItemRepository;
        this.cartItemDTOMapper = cartItemDTOMapper;
        this.orderRepository = orderRepository;
        this.orderDTOMapper = orderDTOMapper;
        this.productService = productService;
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllAdmins() {
        List<UserEntity> admins = userEntityRepository.fetchAllAdmins();
        List<UserEntityDTO> userEntityDTOList = admins.stream().map(userEntityDTOMapper).toList();
        CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllClients() {
        List<UserEntity> clients = userEntityRepository.fetchAllClients();
        List<UserEntityDTO> userEntityDTOList = clients.stream().map(userEntityDTOMapper).toList();
        CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllTechnicians() {
        List<UserEntity> technicians = userEntityRepository.fetchAllTechnicians();
        List<UserEntityDTO> userEntityDTOList = technicians.stream().map(userEntityDTOMapper).toList();
        CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> enableUser(UUID userId) {
        final UserEntity user = getUserEntityById(userId);
        user.setEnabled(true);
        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(userEntityRepository.save(user));
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDto);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> disableUser(UUID userId) {
        final UserEntity user = getUserEntityById(userId);
        user.setEnabled(false);
        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(userEntityRepository.save(user));
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDto);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(final UUID userId) {
        final UserEntity user = getUserEntityById(userId);

        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(user);
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDto);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }



    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllUsers()
    {
        final List<UserEntityDTO> userEntityFullDTOList = userEntityRepository.fetchAllUsers().stream().map(userEntityDTOMapper).toList();
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,userEntityFullDTOList));
    }
    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(@NotNull final UserDetails userDetails)
    {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        final UserEntityDTO currentUserDto = userEntityDTOMapper.apply(currentUser);
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,currentUserDto);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);

    }

    public long countClientsByMonth(YearMonth month) {
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.plusMonths(1).atDay(1).atStartOfDay();
        return userEntityRepository.countClientsByMonth(start, end);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<Map<String, Long>>> countClientsByYear(int year) {
        Map<String, Long> clientsCountByMonth = new LinkedHashMap<>();
        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(year, month);
            long count = countClientsByMonth(yearMonth);
            clientsCountByMonth.put(String.format("%d-%02d", year, month), count);
        }
        final CustomResponseEntity<Map<String, Long>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,clientsCountByMonth);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> updateUser(@NotNull UserDetails userDetails,final UserEntity userEntity){
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        currentUser.setFirstName(userEntity.getFirstName());
        currentUser.setLastName(userEntity.getLastName());
        currentUser.setPhoneNumber(userEntity.getPhoneNumber());
        final UserEntityDTO currentUserDto = userEntityDTOMapper.apply(userEntityRepository.save(currentUser));
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,currentUserDto);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<String>> addItemToBasket(@NotNull UserDetails userDetails, @NotNull CartDTO cartDTO) {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        if(!currentUser.getRole().getName().equalsIgnoreCase("CLIENT")){
            throw new UnauthorizedActionException("Only clients can add items to their basket.");
        }
        final Product product = productService.getProductById(cartDTO.getProductId());
        CartItem cartItem = CartItem.builder()
                .quantity(cartDTO.getQuantity())
                .product(product)
                .user(currentUser)
                .build();
        cartItemRepository.save(cartItem);

        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,"Item added to basket successfully."));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<String>> removeItemToBasket(@NotNull UserDetails userDetails, @NotNull Long cartItemId) {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        if(!currentUser.getRole().getName().equalsIgnoreCase("CLIENT")){
            throw new UnauthorizedActionException("Only clients can remove items from their basket.");
        }
        if(currentUser.getCartItems().stream().filter(cartItem -> cartItem.getId().equals(cartItemId)).toList().isEmpty()){
            throw new UnauthorizedActionException("You can only remove items from your basket.");
        }

        CartItem savedCartItem = getCartItemById(cartItemId);
        cartItemRepository.deleteCartItemById(savedCartItem.getId());

        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,"Item removed from basket successfully."));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<String>> updateItemToBasket(@NotNull UserDetails userDetails, @NotNull Long cartItemId, int newQuantity) {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        if(!currentUser.getRole().getName().equalsIgnoreCase("CLIENT")){
            throw new UnauthorizedActionException("Only clients can remove items from their basket.");
        }
        if(currentUser.getCartItems().stream().filter(cartItem -> cartItem.getId().equals(cartItemId)).toList().isEmpty()){
            throw new UnauthorizedActionException("You can only updated items from your basket.");
        }
        CartItem savedCartItem = getCartItemById(cartItemId);
        savedCartItem.setQuantity(newQuantity);
        cartItemRepository.save(savedCartItem);
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,"Item updated in basket successfully."));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<OrderDTO>> checkout(@NotNull UserDetails userDetails) {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        if(!currentUser.getRole().getName().equalsIgnoreCase("CLIENT")){
            throw new UnauthorizedActionException("Only clients can remove items from their basket.");
        }
        final List<CartItem> cartItems = new ArrayList<>(currentUser.getCartItems());

        if(cartItems.isEmpty()){
            throw new UnauthorizedActionException("You can only checkout with items in your basket.");
        }

        double totalPrice = cartItems.stream().mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity()).sum();

        cartItemRepository.saveAll(cartItems.stream().peek(cartItem -> cartItem.setUser(null)).toList());

        final Order order = Order.builder()
                .user(currentUser)
                .totalPrice(totalPrice)
                .orderedAt(LocalDateTime.now())
                .cartItems(cartItems)
                .build();
        currentUser.getCartItems().clear();
        userEntityRepository.save(currentUser);
        final OrderDTO orderDTO = orderDTOMapper.apply(orderRepository.save(order));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,orderDTO));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<CartItemDTO>>> fetchCartItems(@NotNull UserDetails userDetails) {

        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        if(!currentUser.getRole().getName().equalsIgnoreCase("CLIENT")){
            throw new UnauthorizedActionException("Only clients can remove items from their basket.");
        }

        final List<CartItemDTO> cartItemDTOList = currentUser.getCartItems().stream().map(cartItemDTOMapper).toList();

        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,cartItemDTOList));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<OrderDTO>>> fetchAllOrders() {
        final List<Order> orders = orderRepository.fetchAllOrders();
        final List<OrderDTO> orderDTOList = orders.stream().map(orderDTOMapper).toList();
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,orderDTOList));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<OrderDTO>>> fetchAllOrdersByUser(@NotNull UserDetails userDetails) {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        if(!currentUser.getRole().getName().equalsIgnoreCase("CLIENT")){
            throw new UnauthorizedActionException("Only clients can remove items from their basket.");
        }
        final List<Order> orders = orderRepository.fetchOrderOfUserById(currentUser.getId());
        final List<OrderDTO> orderDTOList = orders.stream().map(orderDTOMapper).toList();
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,orderDTOList));
    }


    @Override
    public boolean isEmailRegistered(final String email)
    {
        return userEntityRepository.isEmailRegistered(email);
    }

    @Override
    public UserEntity saveUser(@NotNull final UserEntity userEntity)
    {
        return userEntityRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserEntityById(final UUID userId)
    {
        return userEntityRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID : %s could not be found.", userId)));
    }

    @Override
    public UserEntity getUserEntityByEmail(@NotNull final String userEmail)
    {
        return userEntityRepository.fetchUserWithEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with email : %s could not be found.", userEmail)));
    }


    private CartItem getCartItemById(final Long cartItemId)
    {
        return cartItemRepository.fetchCartItemWithId(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The cart item with ID : %s could not be found.", cartItemId)));
    }
}
