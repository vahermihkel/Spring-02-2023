package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.Category;
import ee.mihkel.webshop.model.database.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// CrudRepository PagingAndSortingRepository JpaRepository
//
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);
}
