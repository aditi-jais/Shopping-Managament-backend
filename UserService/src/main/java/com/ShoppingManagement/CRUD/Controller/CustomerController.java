package com.ShoppingManagement.CRUD.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ShoppingManagement.CRUD.Model.Customer;
import com.ShoppingManagement.CRUD.Service.CustomerService;


@RestController
public class CustomerController {

    @Autowired
    CustomerService CstService;

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        return CstService.getAllCustomer();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        return CstService.getCustomerById(id);
    }



    @PostMapping("/customer")
    public ResponseEntity<String> createCustomer(@RequestBody Customer cst) {
        return CstService.createCustomer(cst);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable("id") int id, @RequestBody Customer cst) {
       return CstService.updateCustomer(id,cst);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {
       return CstService.deleteCustomer(id);
    }

    @DeleteMapping("/customer")
    public ResponseEntity<String> deleteAllCustomer() {
       return CstService.deleteAllCustomer();

    }



}