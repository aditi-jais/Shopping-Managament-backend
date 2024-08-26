package com.ShoppingManagement.OrderService.Controller;

import com.ShoppingManagement.OrderService.Model.Order;
import com.ShoppingManagement.OrderService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class OrderController {

    @Autowired
    OrderService OrdService;

    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAllOrder() {
        return OrdService.getAllOrder();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int id) {
        return OrdService.getOrderById(id);
    }



    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody Order od,@RequestParam(required = true) int cartId,@RequestParam(required = true) int addId) {
        return OrdService.createOrder(od,cartId,addId);
    }



    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") int id) {
        return OrdService.deleteOrder(id);
    }

    @DeleteMapping("/order")
    public ResponseEntity<String> deleteAllOrder() {
        return OrdService.deleteAllOrder();

    }




}
