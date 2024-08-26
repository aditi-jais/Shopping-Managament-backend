package com.ShoppingManagement.OrderService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="inventory")
public class Inventory {

    @Id
    private int invId;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Seller seller;
    private String name;
    private String about;
    private int price;
    private int stock;
}
