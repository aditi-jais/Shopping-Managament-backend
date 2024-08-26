package com.ShoppingManagement.CRUD.Repository;
import com.ShoppingManagement.CRUD.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
