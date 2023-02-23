package ee.mihkel.webshop;

import org.springframework.data.jpa.repository.JpaRepository;

// CrudRepository PagingAndSortingRepository JpaRepository
//
public interface ProductRepository extends JpaRepository<Product, Long> {
}
