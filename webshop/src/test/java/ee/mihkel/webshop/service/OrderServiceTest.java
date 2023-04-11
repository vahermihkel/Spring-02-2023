package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.database.CartRow;
import ee.mihkel.webshop.model.database.Category;
import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.repository.CartRowRepository;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    Category dummyCategory = new Category(1L, "");
    Product dummyProduct = new Product(1L, "", "", 2.0, "", false, 99, dummyCategory);
    Product dummyProduct2 = new Product(2L, "", "", 4.0, "", false, 99, dummyCategory);
    List<CartRow> cart = new ArrayList<>(Arrays.asList(
            new CartRow(1L, dummyProduct,4),
            new CartRow(2L, dummyProduct2,3)
    ));

    @Mock
    PersonRepository personRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartRowRepository cartRowRepository;

    @Mock
    OrderRepository orderRepository;

    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService();
        orderService.orderRepository = this.orderRepository;
        orderService.cartRowRepository = this.cartRowRepository;
        orderService.productRepository = this.productRepository;
        orderService.personRepository = this.personRepository;

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(dummyProduct));

        Mockito.when(productRepository.findById(2L))
                .thenReturn(Optional.of(dummyProduct2));
    }

    @Test
    void getDbProducts() {
//        List<CartRow> cartWithDbProducts = orderService.getDbProducts(cart);
//        assertEquals(2, cartWithDbProducts.size());
        List<CartRow> cartWithDbProducts = orderService.getDbProducts(cart);
        assertEquals(2, cartWithDbProducts.size());
    }

    @Test
    void calculateTotalSum() {
        Double totalsum = orderService.calculateTotalSum(cart);
        assertEquals(20.0, totalsum);
    }

    @Test
    void saveOrder() {
        int ordersSizeBeforeAdding = orderService.orderRepository.findAll().size();
        orderService.saveOrder("","123", cart, 20.0);
        int ordersSizeAfterAdding = orderService.orderRepository.findAll().size();
        int difference = ordersSizeAfterAdding - ordersSizeBeforeAdding;
        assertEquals(1, difference);
    }

    @Test
    void changeOrderToPaid() {
    }
}
