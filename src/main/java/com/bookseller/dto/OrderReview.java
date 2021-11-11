package com.bookseller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReview {
    private List<Sku> skus;
    private double ordersubTotal;
    private double orderTotal;
    private double totalItemDiscounts;
    private double orderDiscount;
}
