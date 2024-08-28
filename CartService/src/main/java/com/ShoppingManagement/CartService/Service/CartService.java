package com.ShoppingManagement.CartService.Service;

import com.ShoppingManagement.CartService.Model.Cart;
import com.ShoppingManagement.CartService.Model.Customer;
import com.ShoppingManagement.CartService.Model.Inventory;
import com.ShoppingManagement.CartService.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CartService {
    @Autowired
    CartRepository CrtRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseEntity<List<Cart>> getAllCart() {
        try {
            List<Cart> crt = new ArrayList<Cart>();

            CrtRepository.findAll().forEach(crt::add);

            if (crt.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(crt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Cart> getCartById(int id) {
        Optional<Cart> crt = CrtRepository.findById(id);

        if (crt.isPresent()) {
            return new ResponseEntity<>(crt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> createCart(Cart crt, int cstId, int[] invId) {
        try {
            List<Inventory> inventories = new ArrayList<>();
            Cart cart = new Cart();

            Customer cst = new IsCustomer(webClientBuilder).checking(cstId);
            if (cst == null) {
                return new ResponseEntity<>("Customer does not exist.", HttpStatus.NOT_FOUND);
            }

            for (int id : invId) {
                Inventory inventory = new IsInventory(webClientBuilder).checking(id);
                if (inventory == null) {
                    return new ResponseEntity<>("Inventory ID " + id + " does not exist.", HttpStatus.NOT_FOUND);
                }
                inventories.add(inventory);
            }
            cart.setCartId(crt.getCartId());
            cart.setCst(cst);
            cart.setInv(inventories);
            CrtRepository.save(cart);

            return new ResponseEntity<>("Cart is created successfully.", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is an error while creating the cart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> AddToCart(int id, int[] invId) {
        Optional<Cart> _crt = CrtRepository.findById(id);

        if (_crt.isPresent()) {
            List<Inventory> inventories = new ArrayList<>();
            for (int i : invId) {
                Inventory inventory = new IsInventory(webClientBuilder).checking(i);
                if (inventory == null) {
                    return new ResponseEntity<>("Inventory ID " + id + " does not exist.", HttpStatus.NOT_FOUND);
                }
                inventories.add(inventory);
            }
            for (Inventory i : _crt.get().getInv()) {
                inventories.add(i);
            }
            _crt.get().setInv(inventories);
            CrtRepository.save(_crt.get());

            return new ResponseEntity<>("Cart was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Cart with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> DeletefromCart(int id, int[] invIdToRemove) {
        Optional<Cart> optionalCart = CrtRepository.findById(id);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<Inventory> existingInventories = cart.getInv();
            List<Inventory> updatedInventories = new ArrayList<>();
            Set<Integer> idsToRemove = Arrays.stream(invIdToRemove).boxed().collect(Collectors.toSet());
            for (Inventory inv : existingInventories) {
                if (!idsToRemove.contains(inv.getInvId())) {
                    updatedInventories.add(inv);
                }
            }
            cart.setInv(updatedInventories);
            CrtRepository.save(cart);

            return new ResponseEntity<>("Cart was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Cart with id=" + id, HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> deleteCart(int id) {
        try {
            Optional<Cart> cst = CrtRepository.findById(id);

            if (cst.isPresent()) {
                CrtRepository.deleteById(id);
                return new ResponseEntity<>("Cart was deleted successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Cannot find Cart with id=" + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteAllCart() {
        try {
            long numRows = CrtRepository.count();
            CrtRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Cart(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
