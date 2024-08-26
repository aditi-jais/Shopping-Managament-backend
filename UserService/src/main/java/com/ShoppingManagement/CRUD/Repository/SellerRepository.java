package com.ShoppingManagement.CRUD.Repository;
import com.ShoppingManagement.CRUD.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
@org.springframework.stereotype.Repository


public interface SellerRepository extends JpaRepository<Seller, Integer> {


}
