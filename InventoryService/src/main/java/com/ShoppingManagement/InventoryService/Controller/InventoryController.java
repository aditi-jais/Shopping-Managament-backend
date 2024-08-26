package com.ShoppingManagement.InventoryService.Controller;

import com.ShoppingManagement.InventoryService.Model.Inventory;
import com.ShoppingManagement.InventoryService.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryController {

    @Autowired
    InventoryService InvService;

    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return InvService.getAllInventory();
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable("id") int id) {
        return InvService.getInventoryById(id);
    }


    @PostMapping("/inventory")
    public ResponseEntity<String> createInventory(@RequestBody Inventory inv, @RequestParam(required = true) int sellerId) {
        return InvService.createInventory(inv, sellerId);
    }

    @PutMapping("/inventory/{id}")
    public ResponseEntity<String> updateInventory(@PathVariable("id") int id, @RequestBody Inventory inv) {
        return InvService.updateInventory(id, inv);
    }

    @DeleteMapping("/inventory/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable("id") int id) {
        return InvService.deleteInventory(id);
    }

    @DeleteMapping("/inventory")
    public ResponseEntity<String> deleteAllInventory() {
        return InvService.deleteAllInventory();

    }


}
