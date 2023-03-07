package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.Category;
import ee.mihkel.webshop.model.database.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
