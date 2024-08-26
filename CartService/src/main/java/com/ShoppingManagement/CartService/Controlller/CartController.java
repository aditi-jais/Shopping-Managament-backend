package com.ShoppingManagement.CartService.Controlller;

import com.ShoppingManagement.CartService.Model.Cart;
import com.ShoppingManagement.CartService.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
public class CartController {

        @Autowired
        CartService CartService;

        @GetMapping("/cart")
        public ResponseEntity<List<Cart>> getAllCart() {
            return CartService.getAllCart();
        }

        @GetMapping("/cart/{id}")
        public ResponseEntity<Cart> getCartById(@PathVariable("id") int id) {
            return CartService.getCartById(id);
        }

        @PostMapping("/cart")
        public ResponseEntity<String> createCart(@RequestBody Cart crt,@RequestParam(required = true) int cstId,@RequestParam(required = true) int[] invId) {
            return CartService.createCart(crt,cstId,invId);
        }

        @PutMapping("/AddToCart/{id}")
        public ResponseEntity<String> AddToCart(@PathVariable("id") int id, @RequestParam(required = true) int[] invId) {
            return CartService.AddToCart(id,invId);
        }
        @DeleteMapping("/DeletefromCart/{id}")
        public ResponseEntity<String> DeletefromCart(@PathVariable("id") int id, @RequestParam(required = true) int[] invId) {
            return CartService.DeletefromCart(id,invId);
        }

        @DeleteMapping("/cart/{id}")
        public ResponseEntity<String> deleteCart(@PathVariable("id") int id) {
            return CartService.deleteCart(id);
        }

        @DeleteMapping("/cart")
        public ResponseEntity<String> deleteAllCart() {
            return CartService.deleteAllCart();

        }




}
