package com.ShoppingManagement.CRUD.Repository;
import com.ShoppingManagement.CRUD.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
