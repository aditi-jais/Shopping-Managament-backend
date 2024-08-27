package com.ShoppingManagement.InventoryService.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invId;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,}$",
            message = "name must be of 3 to 12 length with no special characters")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9()\\s.']{15,}$",
            message = "Description must be of atleast 15 length.")
    private String about;
    private int price;
    private int stock;
}
