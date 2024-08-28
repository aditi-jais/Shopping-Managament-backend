package com.ShoppingManagement.InventoryService.Services;

import com.ShoppingManagement.InventoryService.Model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ShoppingManagement.InventoryService.Repository.InventoryRepository;
import com.ShoppingManagement.InventoryService.Model.Inventory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;


@Service
public class InventoryService {
    @Autowired
    InventoryRepository InvRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseEntity<List<Inventory>> getAllInventory()
    {
        try {
            List<Inventory> inventory = new ArrayList<Inventory>();

            InvRepository.findAll().forEach(inventory::add);

            if (inventory.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(inventory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Inventory> getInventoryById( int id) {
        Optional<Inventory> customer = InvRepository.findById(id);

        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }



    public ResponseEntity<String> createInventory(Inventory inventory,int sellerId) {
        Seller seller = new IsSeller(webClientBuilder).checking(sellerId);
        if (seller != null) {
            try{
                inventory.setSeller(seller);
                InvRepository.save(inventory);
                return new ResponseEntity<>("Inventory was created successfully.", HttpStatus.CREATED);
            }
            catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>("Error while creating inventory."+e.getMessage(), HttpStatus.CREATED);
            }
        }
        else {
            return new ResponseEntity<>("Seller does not exist.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> updateInventory(int id,Inventory inv) {
        Optional<Inventory> _inv = InvRepository.findById(id);

        if (_inv.isPresent()) {
            Inventory existingInv = _inv.get();
            if(inv.getName()!=null)
                existingInv.setName(inv.getName());
            if(inv.getAbout()!=null)
                existingInv.setAbout(inv.getAbout());
            if(inv.getPrice()!=0)
                existingInv.setPrice(inv.getPrice());
            if(inv.getStock()!=0)
                existingInv.setStock(inv.getStock());

            InvRepository.save(existingInv);

            return new ResponseEntity<>("Inventory was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Inventory with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteInventory(int id) {
        try{
        Optional<Inventory> cst = InvRepository.findById(id);

        if (cst.isPresent()) {
            InvRepository.deleteById(id);
            return new ResponseEntity<>("Inventory was deleted successfully.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot find Inventory with id=" + id, HttpStatus.OK);
    } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Inventory.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> deleteAllInventory() {
        try {
            long numRows = InvRepository.count();
            InvRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Customer(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete customer.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
