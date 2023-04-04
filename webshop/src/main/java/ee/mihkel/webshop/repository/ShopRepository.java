package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
