package com.bookseller;

import com.bookseller.controller.CheckoutController;
import com.bookseller.dto.Order;
import com.bookseller.dto.OrderReview;
import com.bookseller.dto.Sku;
import com.bookseller.entity.Book;
import com.bookseller.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckoutController.class)
@AutoConfigureMockMvc
public class CheckoutControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void checkoutTest() throws Exception {

        //given
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

        String jsondata= objectMapper.writeValueAsString(orderInput);
        when(orderService.computeOrder(orderInput)).thenReturn(orderReview);
        //when
        MvcResult result = mockMvc.perform(post("/api/bookserller/checkout/v1/checkout").content(jsondata).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        OrderReview testReturnResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<OrderReview>() {});
       assertEquals("size of returned data",2, testReturnResult.getSkus().size());
       assertEquals("order level discount",144.0,testReturnResult.getOrderDiscount());
    }

    @Test
    void checkoutEmptyTest() throws Exception {

        //given
        List<Book> bookList = new ArrayList();
        Order orderInput = Order.builder().books(bookList).promotionCode("PROMO20OFF").build();

        List<Sku> skuList = new ArrayList<>();
        Sku sku1 = Sku.builder().itemName("five point").markPrice(300.0).discount(30.0).listPrice(270.0).promotionApplied("10.0% off").build();
        Sku sku2 = Sku.builder().itemName("got").markPrice(500.0).discount(50.0).listPrice(450.0).promotionApplied("10.0% off").build();
        skuList.add(sku1);
        skuList.add(sku2);

        OrderReview orderReview = OrderReview.builder().skus(skuList).orderDiscount(144.0).ordersubTotal(576.0).orderTotal(800.0)
                .totalItemDiscounts(80.0).build();

        String jsondata= objectMapper.writeValueAsString(orderInput);
        when(orderService.computeOrder(orderInput)).thenReturn(orderReview);
        //when
        MvcResult result = mockMvc.perform(post("/api/bookserller/checkout/v1/checkout").content(jsondata).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertEquals("order level discount","No item added to cart for checkout",result.getResponse().getContentAsString());
    }
}
