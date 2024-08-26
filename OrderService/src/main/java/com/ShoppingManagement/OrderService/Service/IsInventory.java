package com.ShoppingManagement.OrderService.Service;

import com.ShoppingManagement.OrderService.Model.Inventory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class IsInventory {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public IsInventory(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://inventory-service").build();
        this.objectMapper = new ObjectMapper();
    }

//    public Inventory checkInventory(int id) {
//        try {
//            // Fetch the inventory item
//            return webClient.get()
//                    .uri("/inventory/" + id)
//                    .retrieve()
//                    .bodyToMono(Inventory.class)
//                    .block();  // Blocking here for simplicity; consider using reactive streams
//        } catch (Exception e) {
//            // Log the exception and handle it
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void updateInventory(int id, int quantity) {
        try {
            // Fetch the current inventory item
            String inventoryJson = webClient.get()
                    .uri("/inventory/" + id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse and update the stock value
            JsonNode rootNode = objectMapper.readTree(inventoryJson);
            int stock = rootNode.path("stock").asInt();
            System.out.println("Current stock available: " + stock);

            // Update the stock value
            stock -= quantity;
            ((ObjectNode) rootNode).put("stock", stock);
            String updatedInventoryJson = objectMapper.writeValueAsString(rootNode);

            // Send a PUT request to update the inventory
            webClient.put()
                    .uri("/inventory/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(updatedInventoryJson)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(responseBody -> System.out.println("Update response - " + responseBody))
                    .block();  // Blocking here for simplicity; consider using reactive streams

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
