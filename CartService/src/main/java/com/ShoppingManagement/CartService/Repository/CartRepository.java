package com.ShoppingManagement.CartService.Repository;
import com.ShoppingManagement.CartService.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {


}
