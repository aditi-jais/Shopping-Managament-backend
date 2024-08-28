package com.ShoppingManagement.CartService.Service;

import com.ShoppingManagement.CartService.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IsCustomer {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public IsCustomer(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private Customer s;
    public Customer checking(int id)
    {
        try{
            s = webClientBuilder.build()
                    .get()
                    .uri("http://USER-SERVICE/customer/" + id)
                    .retrieve()
                    .bodyToMono(Customer.class)
                    .block();
}
catch(Exception e){
    e.printStackTrace();
    System.out.println("Error from Cart Service"+e);
}
        return s;
    }


}
