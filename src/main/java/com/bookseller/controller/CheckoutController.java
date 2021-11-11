package com.bookseller.controller;

import com.bookseller.dto.Order;
import com.bookseller.dto.OrderReview;
import com.bookseller.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/bookserller/checkout/v1")
public class CheckoutController {
    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/checkout")
    public ResponseEntity<?> checkout(@RequestBody Order order){
        if(order.getBooks().size()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No item added to cart for checkout");
        }
        OrderReview orderReview = orderService.computeOrder(order);
        return  ResponseEntity.status(HttpStatus.OK).body(orderReview);
    }
}
