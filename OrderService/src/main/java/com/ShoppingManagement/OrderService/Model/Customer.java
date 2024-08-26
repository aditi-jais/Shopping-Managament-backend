package com.ShoppingManagement.OrderService.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customer")
public class Customer {
    @Id
    private int cstId;
    private String email;
    private String firstname;
    private String lastname;
    private String phone_no;


}
