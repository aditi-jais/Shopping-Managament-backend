package com.ShoppingManagement.OrderService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int orderId;
    private String orderName;
   private LocalDate orderDate;
   private int price;
   private int quantity;
   @ManyToOne
   @JoinColumn(name="cstId")
  private  Customer cst;

    @ManyToOne
    @JoinColumn(name="addressId")
    private Address add;
   private int progress;
}
