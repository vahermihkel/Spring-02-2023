package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.CartRow;
import ee.mihkel.webshop.model.database.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRowRepository extends JpaRepository<CartRow, Long> {
}
