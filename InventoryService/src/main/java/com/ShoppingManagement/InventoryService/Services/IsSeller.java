package com.ShoppingManagement.InventoryService.Services;

import com.ShoppingManagement.InventoryService.Model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IsSeller {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public IsSeller(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    public Seller checking(int id) {
        Seller s = null;
        try {
            s = webClientBuilder.build()
                    .get()
                    .uri("http://USER-SERVICE/seller/" + id)
                    .retrieve()
                    .bodyToMono(Seller.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error is:"+e);
        }
        return s;
    }
}
