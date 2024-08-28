package com.ShoppingManagement.CRUD.Controller;

import com.ShoppingManagement.CRUD.Model.Address;
import com.ShoppingManagement.CRUD.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    AddressService AddService;

    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAllAddress() {
        return AddService.getAllAddress();
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable("id") int id) {
        return AddService.getAddressById(id);
    }
    @GetMapping("/Cstaddress/{id}")
    public ResponseEntity<List<Address>> getAddressByCst(@PathVariable("id") int id) {
        return AddService.getAddressByCst(id);
    }



    @PostMapping("/address")
    public ResponseEntity<String> createAddress(@RequestBody Address add,@RequestParam(required = true) int cstId) {
        return AddService.createAddress(add,cstId);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable("id") int id, @RequestBody Address cst) {
        return AddService.updateAddress(id,cst);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") int id) {
        return AddService.deleteAddress(id);
    }

    @DeleteMapping("/address")
    public ResponseEntity<String> deleteAllAddress() {
        return AddService.deleteAllAddress();

    }

}
