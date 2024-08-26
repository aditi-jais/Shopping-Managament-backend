package com.ShoppingManagement.CRUD.Service;
import com.ShoppingManagement.CRUD.Model.Address;
import com.ShoppingManagement.CRUD.Model.Customer;
import com.ShoppingManagement.CRUD.Model.Seller;
import com.ShoppingManagement.CRUD.Repository.AddressRepository;
import com.ShoppingManagement.CRUD.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Service?????
@RestController
public class CustomerService {
    @Autowired
    CustomerRepository CstRepository;
    AddressRepository AddRepository;
    public ResponseEntity<List<Customer>> getAllCustomer()
    {
        try {
            List<Customer> customer = new ArrayList<Customer>();

            CstRepository.findAll().forEach(customer::add);

            if (customer.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Customer> getCustomerById( int id) {
        Optional<Customer> customer = CstRepository.findById(id);

        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> createCustomer( Customer cst) {
        try {
            List<Customer> customer = new ArrayList<Customer>();
            CstRepository.findAll().forEach(customer::add);
            for(Customer c:customer){
                if(Objects.equals(c.getEmail(), cst.getEmail()))
                    return new ResponseEntity<>("Customer with this email ID is already exist.", HttpStatus.CREATED);
            if(Objects.equals(c.getPhone_no(), cst.getPhone_no()))
                    return new ResponseEntity<>("Customer with this contact number is already exist.", HttpStatus.CREATED);
            }

            CstRepository.save(cst);
            return new ResponseEntity<>("Customer was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is an error while creating the customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<String> updateCustomer(int id,Customer cst) {
        Optional<Customer> _cst = CstRepository.findById(id);

        if (_cst.isPresent()) {
            Customer existingCst = _cst.get();
            existingCst.setEmail(cst.getEmail());
            existingCst.setFirstname(cst.getFirstname());
            existingCst.setLastname(cst.getLastname());
            existingCst.setPhone_no(cst.getPhone_no());

            CstRepository.save(existingCst);
            return new ResponseEntity<>("Customer was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Customer with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteCustomer(int id) {
        try {
            Optional<Customer> cst = CstRepository.findById(id);

            if (cst.isPresent()) {
                CstRepository.deleteById(id);
                return new ResponseEntity<>("Customer was deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find customer with id=" + id, HttpStatus.OK);
            }} catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Customer.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> deleteAllCustomer() {
        try {
            long numRows = CstRepository.count();
            CstRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Customer(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete customer.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
