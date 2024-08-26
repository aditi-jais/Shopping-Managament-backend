package com.ShoppingManagement.OrderService.Service;

import com.ShoppingManagement.OrderService.Model.Address;
import com.ShoppingManagement.OrderService.Model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IsAdd
{
//    private RestTemplate restTemplate;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public IsAdd(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    private Address s;
    public Address checking(int id)
    {
//        RestTemplate restTemplate = new RestTemplate();
        try{

//            s=restTemplate.getForObject("http://localhost:8080/address/"+ id,Address.class);
            s = webClientBuilder.build()
                    .get()
                    .uri("http://USER-SERVICE/address/" + id)
                    .retrieve()
                    .bodyToMono(Address.class)
                    .block();
//    result =true;
        }
        catch(Exception e){
//    result=false;
        }
        return s;
    }


}
