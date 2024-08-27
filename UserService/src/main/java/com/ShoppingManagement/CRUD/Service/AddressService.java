package com.ShoppingManagement.CRUD.Service;
import com.ShoppingManagement.CRUD.Model.Address;
import com.ShoppingManagement.CRUD.Model.Customer;
import com.ShoppingManagement.CRUD.Repository.AddressRepository;
import com.ShoppingManagement.CRUD.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service?????
@RestController
public class AddressService {
    @Autowired
    AddressRepository AddRepository;
    @Autowired
    CustomerRepository CstRepository;

    public ResponseEntity<List<Address>> getAllAddress()
    {
        try {
            List<Address> address = new ArrayList<Address>();

            AddRepository.findAll().forEach(address::add);

            if (address.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Address> getAddressById( int id) {
        Optional<Address> address = AddRepository.findById(id);

        if (address.isPresent()) {
            return new ResponseEntity<>(address.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }public ResponseEntity<List<Address>> getAddressByCst( int id) {
        try {
            Optional<Customer> cst = CstRepository.findById(id);
            if (cst.isPresent()) {
                List<Address> addresses =  new ArrayList<Address>();
                List<Address> address =  new ArrayList<Address>();
                AddRepository.findAll().forEach(addresses::add);

                for (Address address1 : addresses) {
                    if(address1.getCst()==cst.get())
                        address.add(address1);
                }

                if (address.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(address, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> createAddress( Address add,int cstId) {
        try {
            Optional<Customer> cst = CstRepository.findById(cstId);
            if (cst.isPresent()) {
                Customer existingCst = cst.get();
                add.setCst(existingCst);
                AddRepository.save(add);
                return new ResponseEntity<>("Address is created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Customer does not exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while creating address."+e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> updateAddress(int id,Address add) {
        Optional<Address> _add = AddRepository.findById(id);

        if (_add.isPresent()) {
            Address add1 = _add.get();
            if(add.getStreet()!=null)
                add1.setStreet(add.getStreet());
            if(add.getState()!=null)
                add1.setState(add.getState());
            if(add.getCountry()!=null)
                add1.setCountry(add.getCountry());
            if(add.getLandmark()!=null)
                add1.setLandmark(add.getLandmark());

            AddRepository.save(add1);
            return new ResponseEntity<>("Address was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Address with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAddress(int id) {
        try {
            Optional<Address> cst = AddRepository.findById(id);

            if (cst.isPresent()) {
                AddRepository.deleteById(id);
                return new ResponseEntity<>("Address was deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find address with id=" + id, HttpStatus.OK);
            }} catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Address.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> deleteAllAddress() {
        try {
            long numRows = AddRepository.count();
            AddRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Address(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete address.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
