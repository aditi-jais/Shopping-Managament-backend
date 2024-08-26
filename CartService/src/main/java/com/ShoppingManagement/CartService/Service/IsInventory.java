package com.ShoppingManagement.CartService.Service;

import com.ShoppingManagement.CartService.Model.Customer;
import com.ShoppingManagement.CartService.Model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

//@Service
public class IsInventory {
//    private RestTemplate restTemplate;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public IsInventory(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private Inventory s;
    public Inventory checking(int id)
    {
//        RestTemplate restTemplate = new RestTemplate();
        try{

//            s=restTemplate.getForObject("http://localhost:8081/inventory/"+ id,Inventory.class);

            s = webClientBuilder.build()
                    .get()
                    .uri("http://inventory-service/inventory/" + id)
                    .retrieve()
                    .bodyToMono(Inventory.class)
                    .block();

//    result =true;
        }
        catch(Exception e){
//    result=false;
        }
        return s;
    }


}
