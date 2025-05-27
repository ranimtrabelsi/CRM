package org.taskspfe.pfe.model.shop;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.taskspfe.pfe.model.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "orders")
public class Order {

    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "order_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name ="odered_at")
    private LocalDateTime orderedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<CartItem> cartItems;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
