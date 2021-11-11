package com.bookseller.service;

import com.bookseller.dto.Order;
import com.bookseller.dto.OrderPromotion;
import com.bookseller.dto.OrderReview;
import com.bookseller.dto.Sku;
import com.bookseller.entity.Book;
import com.bookseller.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private BookRepository bookRepository;

    public OrderReview computeOrder(Order order) {
        List<Sku> skuList = new ArrayList();
        for (Book book : order.getBooks()) {
            if (bookRepository.existsById(book.getId())) {
                Sku sku = Sku.builder().markPrice(book.getPrice()).itemName(book.getName()).build();
                double discountPercent = 0;
                if (book.getType().equalsIgnoreCase("fiction")) {
                    discountPercent = 10;
                } else if (book.getType().equalsIgnoreCase("comic")) {
                    discountPercent = 0;
                }
                sku.setPromotionApplied(String.valueOf(discountPercent) + "% off");
                sku.setDiscount(((book.getPrice() * discountPercent) / 100));
                sku.setListPrice(book.getPrice() - sku.getDiscount());
                skuList.add(sku);
            }
        }
        OrderReview orderReview = OrderReview.builder().build();
        orderReview.setSkus(skuList);
        orderReview.setOrderTotal(skuList.stream().mapToDouble(sku -> sku.getMarkPrice()).sum());
        orderReview.setTotalItemDiscounts(skuList.stream().mapToDouble(sku -> sku.getDiscount()).sum());
        orderReview.setOrdersubTotal(skuList.stream().mapToDouble(sku -> sku.getListPrice()).sum());
        if (order.getPromotionCode() != null) {
            OrderPromotion orderPromotion = OrderPromotion.valueOf(order.getPromotionCode());
            double orderDiscountAmt=((orderReview.getOrdersubTotal()*orderPromotion.getValue())/100);
            orderReview.setOrderDiscount(orderDiscountAmt);
            orderReview.setOrdersubTotal(orderReview.getOrdersubTotal()-orderDiscountAmt);
        }

        return orderReview;
    }
}
