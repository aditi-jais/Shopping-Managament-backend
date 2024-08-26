package com.ShoppingManagement.OrderService.Service;

import com.ShoppingManagement.OrderService.Model.Address;
import com.ShoppingManagement.OrderService.Model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//@Service
public class IsCart
{

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public IsCart(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    private Cart s;
    public Cart checking(int id)
    {
        RestTemplate restTemplate = new RestTemplate();
        try{

//            s=restTemplate.getForObject("http://localhost:8082/cart/"+ id,Cart.class);

            s = webClientBuilder.build()
                    .get()
                    .uri("http://cart-service/cart/" + id)
                    .retrieve()
                    .bodyToMono(Cart.class)
                    .block();
//    result =true;
        }
        catch(Exception e){
//    result=false;
        }
        return s;
    }

    public void Delete(int cartId)
    {
        try {

            Mono<Void> responseMono = webClientBuilder.build()
                    .delete()
                    .uri("http://cart-service/cart/" + cartId)
                    .retrieve()
                    .bodyToMono(Void.class);


            responseMono.block();

            System.out.println("Cart with ID " + cartId + " has been deleted.");
        } catch (Exception e) {
            // Log the exception and handle it
            e.printStackTrace();
        }
    }


}
