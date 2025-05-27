package org.taskspfe.pfe.model.shop;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.taskspfe.pfe.model.product.Product;
import org.taskspfe.pfe.model.user.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @SequenceGenerator(
            name = "cart_item_sequence",
            sequenceName = "cart_item_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "cart_item_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;




}
