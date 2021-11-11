package com.bookseller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sku {
    String itemName;
    double markPrice;
    double listPrice;
    double discount;
    String promotionApplied;
}
