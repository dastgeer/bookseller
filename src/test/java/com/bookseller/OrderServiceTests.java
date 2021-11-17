package com.bookseller;

import com.bookseller.dto.Order;
import com.bookseller.dto.OrderReview;
import com.bookseller.dto.Sku;
import com.bookseller.entity.Book;
import com.bookseller.repository.BookRepository;

import com.bookseller.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    public void computeOrderTestWithEmptyData() {

        List<Book> bookList = new ArrayList();
        Book book = Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
        Book book2 = Book.builder().id(Long.valueOf(2)).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();

        bookList.add(book);
        bookList.add(book2);

        Order orderInput = Order.builder().books(bookList).promotionCode("PROMO20OFF").build();

        List<Sku> skuList = new ArrayList<>();
        Sku sku1 = Sku.builder().itemName("five point").markPrice(300.0).discount(30.0).listPrice(270.0).promotionApplied("10.0% off").build();
        Sku sku2 = Sku.builder().itemName("got").markPrice(500.0).discount(50.0).listPrice(450.0).promotionApplied("10.0% off").build();
        skuList.add(sku1);
        skuList.add(sku2);

        OrderReview orderReview = OrderReview.builder().skus(skuList).orderDiscount(144.0).ordersubTotal(576.0).orderTotal(800.0)
                .totalItemDiscounts(80.0).build();
        OrderReview testOrderReview= orderService.computeOrder(orderInput);
        assertEquals("size sku list",0, testOrderReview.getSkus().size());
    }

    @Test
    public void computeOrderTestWithData() {

        List<Book> bookList = new ArrayList();
        Book book = Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
        Book book2 = Book.builder().id(Long.valueOf(2)).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();

        bookList.add(book);
        bookList.add(book2);

        Order orderInput = Order.builder().books(bookList).promotionCode("PROMO20OFF").build();

        List<Sku> skuList = new ArrayList<>();
        Sku sku1 = Sku.builder().itemName("five point").markPrice(300.0).discount(30.0).listPrice(270.0).promotionApplied("10.0% off").build();
        Sku sku2 = Sku.builder().itemName("got").markPrice(500.0).discount(50.0).listPrice(450.0).promotionApplied("10.0% off").build();
        skuList.add(sku1);
        skuList.add(sku2);

        OrderReview orderReview = OrderReview.builder().skus(skuList).orderDiscount(144.0).ordersubTotal(576.0).orderTotal(800.0)
                .totalItemDiscounts(80.0).build();
        // when(bookRepository.existsById(book.getId())).thenReturn(true);
        // when( bookRepository.existsById(Long.valueOf(1))).thenReturn(true);
        OrderReview testOrderReview= orderService.computeOrder(orderInput);
        System.out.println("test order review-->"+testOrderReview);
    }
}
