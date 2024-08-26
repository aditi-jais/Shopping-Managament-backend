package com.ShoppingManagement.CartService.Service;

import com.ShoppingManagement.CartService.Model.Customer;
import com.ShoppingManagement.CartService.Model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IsCustomer {
//    private RestTemplate restTemplate;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public IsCustomer(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private Customer s;
    public Customer checking(int id)
    {
//        RestTemplate restTemplate = new RestTemplate();
        try{

//     s=restTemplate.getForObject("http://localhost:8080/customer/"+ id,Customer.class);

            s = webClientBuilder.build()
                    .get()
                    .uri("http://USER-SERVICE/customer/" + id)
                    .retrieve()
                    .bodyToMono(Customer.class)
                    .block();
//    result =true;
}
catch(Exception e){
//    result=false;
}
        return s;
    }


}
