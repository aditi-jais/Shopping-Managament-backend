package com.ShoppingManagement.OrderService.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="address")
public class Address {
    @Id
    private int addressId;
    @ManyToOne
    @JoinColumn(name = "cstId")
    private Customer cst;
    private String street;
    private String landmark;
    private String state;
    private String country;
}
