package com.ShoppingManagement.CRUD.Controller;
import java.util.List;

import com.ShoppingManagement.CRUD.Model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ShoppingManagement.CRUD.Service.SellerService;


@RestController
public class SellerController {

    @Autowired
    SellerService SelService;

    @GetMapping("/seller")
    public ResponseEntity<List<Seller>> getAllSeller() {
        return SelService.getAllSeller();
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable("id") int id) {
        return SelService.getSellerById(id);
    }



    @PostMapping("/seller")
    public ResponseEntity<String> createSeller(@RequestBody Seller cst) {
        return SelService.createSeller(cst);
    }

    @PutMapping("/seller/{id}")
    public ResponseEntity<String> updateSeller(@PathVariable("id") int id, @RequestBody Seller cst) {
        return SelService.updateSeller(id,cst);
    }

    @DeleteMapping("/seller/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable("id") int id) {
        return SelService.deleteSeller(id);
    }

    @DeleteMapping("/seller")
    public ResponseEntity<String> deleteAllSeller() {
        return SelService.deleteAllSeller();

    }
}
