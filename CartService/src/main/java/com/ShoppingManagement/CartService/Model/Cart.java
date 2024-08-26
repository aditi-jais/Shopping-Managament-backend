package com.ShoppingManagement.CartService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    @OneToOne
    @JoinColumn(name = "cstId")
    private Customer cst;
    @ManyToMany
    @JoinTable(
            name = "cart_inventory",
            joinColumns = @JoinColumn(name = "cartId"),
            inverseJoinColumns = @JoinColumn(name = "invId")
    )
    private List<Inventory> inv;
}
