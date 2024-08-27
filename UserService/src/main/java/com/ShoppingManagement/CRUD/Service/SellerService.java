package com.ShoppingManagement.CRUD.Service;
import com.ShoppingManagement.CRUD.Model.Customer;
import com.ShoppingManagement.CRUD.Model.Seller;
import com.ShoppingManagement.CRUD.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class SellerService {
    @Autowired
    SellerRepository SelRepository;
    public ResponseEntity<List<Seller>> getAllSeller()
    {
        try {
            List<Seller> seller = new ArrayList<Seller>();

            SelRepository.findAll().forEach(seller::add);

            if (seller.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(seller, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Seller> getSellerById( int id) {
        Optional<Seller> seller = SelRepository.findById(id);

        if (seller.isPresent()) {
            return new ResponseEntity<>(seller.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> createSeller( Seller sel) {
        try {
            List<Seller> seller = new ArrayList<Seller>();
            SelRepository.findAll().forEach(seller::add);
            for(Seller c:seller){
                if(Objects.equals(c.getEmail(), sel.getEmail()))
                    return new ResponseEntity<>("Seller with this email ID is already exist.", HttpStatus.CREATED);
            }
            SelRepository.save(sel);
            return new ResponseEntity<>("Seller was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is an error while creating the seller: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<String> updateSeller(int id,Seller sel) {
        Optional<Seller> _sel = SelRepository.findById(id);

        if (_sel.isPresent()) {
            Seller existingSeller = _sel.get();
            if(sel.getFirstname()!=null)
                existingSeller.setFirstname(sel.getFirstname());
            if(sel.getLastname()!=null)
                existingSeller.setLastname(sel.getLastname());
            if(sel.getEmail()!=null)
                existingSeller.setEmail(sel.getEmail());

            SelRepository.save(existingSeller);
            return new ResponseEntity<>("Seller was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Seller with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteSeller(int id) {
        try {
            Optional<Seller> seller = SelRepository.findById(id);

            if (seller.isPresent()) {
                SelRepository.deleteById(id);
                return new ResponseEntity<>("Seller was deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find Seller with id=" + id, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Seller.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> deleteAllSeller() {

        try {
            long numRows = SelRepository.count();
            SelRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Seller(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete sellers.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
