package com.ShoppingManagement.OrderService.Service;

import com.ShoppingManagement.OrderService.Model.Address;
import com.ShoppingManagement.OrderService.Model.Cart;
import com.ShoppingManagement.OrderService.Model.Inventory;
import com.ShoppingManagement.OrderService.Model.Order;
import com.ShoppingManagement.OrderService.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    @Autowired
    OrderRepository OrdRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseEntity<List<Order>> getAllOrder() {
        try {
            List<Order> order = new ArrayList<Order>();

            OrdRepository.findAll().forEach(order::add);

            if (order.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Order> getOrderById(int id) {
        Optional<Order> ord = OrdRepository.findById(id);

        if (ord.isPresent()) {
            return new ResponseEntity<>(ord.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> createOrder(Order od, int cartId, int addId) {
        try {
            IsCart crtObj = new IsCart(webClientBuilder);
            Cart crt = crtObj.checking(cartId);

            IsAdd addObj = new IsAdd(webClientBuilder);
            Address ad = addObj.checking(addId);

            if (crt == null) {
                return new ResponseEntity<>("Cart id" + cartId + " not found", HttpStatus.NOT_FOUND);
            }

            if (ad == null || ad.getCst().getCstId() != crt.getCst().getCstId()) {
                return new ResponseEntity<>("Address id" + addId + " not found for given customer.", HttpStatus.NOT_FOUND);
            }

            LocalDate date = LocalDate.now();
            List<Inventory> inv = crt.getInv();
            int noOfOrder = 0;
            List<Integer> errorOrder = new ArrayList<>();

            for (Inventory i : inv) {
                if (i.getStock() >= od.getQuantity()) {
                    noOfOrder++;

                    new IsInventory(webClientBuilder).updateInventory(i.getInvId(), od.getQuantity());

                    Order order = new Order();
                    order.setOrderName(i.getName());
                    order.setCst(crt.getCst());

                    order.setAdd(ad);
                    order.setOrderDate(date);
                    order.setProgress(od.getProgress());
                    order.setPrice(i.getPrice());
                    order.setQuantity(od.getQuantity());

                    OrdRepository.save(order);
//                  delete cart
                    crtObj.Delete(cartId);


                } else {
                    errorOrder.add(i.getInvId());
                }
            }

            String responseMessage = noOfOrder + " Order created. ";
            if (!errorOrder.isEmpty()) {
                responseMessage += "Failed to create order for inventory with ID: " + errorOrder;
            }

            HttpStatus status = noOfOrder > 0 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(responseMessage, status);

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<>("There is an error while creating the order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    public ResponseEntity<String> updateOrder(int id,Order inv) {
//        Optional<Inventory> _inv = InvRepository.findById(id);
//
//        if (_inv.isPresent()) {
//            Inventory existingInv = _inv.get();
////            existingInv.setSeller(inv.getSellerId());
//            existingInv.setName(inv.getName());
////            existingInv.setSeller(inv.getSeller());
//            existingInv.setAbout(inv.getAbout());
//            existingInv.setPrice(inv.getPrice());
//            existingInv.setStock(inv.getStock());
//
//            InvRepository.save(existingInv);
//
//            return new ResponseEntity<>("Inventory was updated successfully.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Cannot find Inventory with id=" + id, HttpStatus.NOT_FOUND);
//        }
//    }

    public ResponseEntity<String> deleteOrder(int id) {
        try {
            Optional<Order> ord = OrdRepository.findById(id);

            if (ord.isPresent()) {
                OrdRepository.deleteById(id);
                return new ResponseEntity<>("Order was deleted successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Cannot find Order with id=" + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Order.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteAllOrder() {
        try {
            long numRows = OrdRepository.count();
            OrdRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Order(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete order.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
