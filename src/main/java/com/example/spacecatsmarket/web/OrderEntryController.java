package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.DTO.order.OrderEntryDTO;
import com.example.spacecatsmarket.service.OrderEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/entry")
@RequiredArgsConstructor
public class OrderEntryController {
    private final OrderEntryService orderEntryService;
    @PostMapping("/{orderId}/products")
    public ResponseEntity<OrderEntryDTO> addProductToOrder(
            @PathVariable Long orderId,
            @RequestParam Long productId,
            @RequestParam int amount) {
        OrderEntryDTO orderEntry = orderEntryService.addProductToOrder(orderId, productId, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderEntry);
    }
    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        orderEntryService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{orderId}/products")
    public ResponseEntity<List<OrderEntryDTO>> getOrderEntries(@PathVariable Long orderId) {
        List<OrderEntryDTO> entries = orderEntryService.getOrderEntries(orderId);
        return ResponseEntity.ok(entries);
    }
}