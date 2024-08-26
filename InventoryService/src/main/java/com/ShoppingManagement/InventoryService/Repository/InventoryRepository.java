package com.ShoppingManagement.InventoryService.Repository;
import com.ShoppingManagement.InventoryService.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

}
