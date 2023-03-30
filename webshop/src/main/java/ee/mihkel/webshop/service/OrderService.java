package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.database.CartRow;
import ee.mihkel.webshop.model.database.Order;
import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.repository.CartRowRepository;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRowRepository cartRowRepository;

    @Autowired // constructor(private http: HttpClient)
    OrderRepository orderRepository;

    public List<CartRow> getDbProducts(List<CartRow> products) {
        List<CartRow> dbProducts = new ArrayList<>();
        for (CartRow p: products) {
            Product dbProduct = productRepository.findById(p.getProduct().getId()).get();
            CartRow cartRow = new CartRow();
            cartRow.setProduct(dbProduct); // <---- Uus, selle nimel seda funktsiooni teeme
            cartRow.setQuantity(p.getQuantity());
            dbProducts.add(cartRow);

            int newStock = dbProduct.getStock()-p.getQuantity();
            dbProduct.setStock(newStock);
        }
        return  dbProducts;
    }

    public double calculateTotalSum(List<CartRow> products) {
        double totalSum = 0;
        // <div *ngFor="let p of products"></div>
        for (CartRow p: products) {
            totalSum += p.getProduct().getPrice() * p.getQuantity();
        }
        // stream() - ava vool, konverteeritakse igaüks hinnaks (double kujule)
        totalSum = products
                        .stream()
                        .mapToDouble(e -> e.getProduct().getPrice() * e.getQuantity())
                        .sum();
        return totalSum;
    }

    @Transactional
    public Long saveOrder(String personalCode, List<CartRow> products, double totalSum) {
        Person person = personRepository.findById(personalCode).get();

        Order order = new Order();
        cartRowRepository.saveAll(products);
        order.setCartRow(products);
        order.setPaid(false);
        order.setPerson(person);
        order.setCreated(new Date());
        order.setTotalSum(totalSum);
        return orderRepository.save(order).getId();
    }

    public void changeOrderToPaid(String orderReference) {
        Long orderId = Long.parseLong(orderReference);
        Order order = orderRepository.findById(orderId).get();
        order.setPaid(true);
        orderRepository.save(order);
    }
}
