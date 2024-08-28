package com.ShoppingManagement.CartService.Service;

import com.ShoppingManagement.CartService.Model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IsInventory {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public IsInventory(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private Inventory s;
    public Inventory checking(int id)
    {

        try{

            s = webClientBuilder.build()
                    .get()
                    .uri("http://inventory-service/inventory/" + id)
                    .retrieve()
                    .bodyToMono(Inventory.class)
                    .block();

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error from Cart Service"+e);
        }
        return s;
    }


}
