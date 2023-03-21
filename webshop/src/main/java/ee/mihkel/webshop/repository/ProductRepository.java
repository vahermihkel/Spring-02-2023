package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.Category;
import ee.mihkel.webshop.model.database.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// CrudRepository PagingAndSortingRepository JpaRepository
//
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByActiveAndStockGreaterThan(boolean active, int stock, Pageable pageable);

    Page<Product> findAllByCategoryAndActiveEquals(Category category, boolean active, Pageable pageable);

    List<Product> findAllByOrderById();
}
