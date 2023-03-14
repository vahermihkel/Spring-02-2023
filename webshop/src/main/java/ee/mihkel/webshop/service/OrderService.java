package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.database.Order;
import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    PersonRepository personRepository;

    @Autowired // constructor(private http: HttpClient)
    OrderRepository orderRepository;

    public double calculateTotalSum(List<Product> products) {
        double totalSum = 0;
        // <div *ngFor="let p of products"></div>
        for (Product p: products) {
            totalSum += p.getPrice();
        }
        return totalSum;
    }

    public Long saveOrder(String personalCode, List<Product> products, double totalSum) {
        Person person = personRepository.findById(personalCode).get();

        Order order = new Order();
        order.setOrderProducts(products);
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
