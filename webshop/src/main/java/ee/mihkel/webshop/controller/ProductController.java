package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.repository.ProductRepository;
import ee.mihkel.webshop.model.database.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200") // pääseb muule ligi, ülejäänutel CORS error
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("product")
    public ResponseEntity<Page<Product>> getProducts(Pageable pageable) {
        return ResponseEntity.ok().body(productRepository.findAllByActiveAndStockGreaterThan(true,0,pageable));
    }

    @GetMapping("admin-products")
    public ResponseEntity<List<Product>> getAdminProducts() {
        return ResponseEntity.ok().body(productRepository.findAllByOrderById());
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(productRepository.findById(id).get());
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().body(productRepository.findAllByOrderById());
    }

    @PostMapping("product")
    public ResponseEntity<List<Product>> addProduct(@RequestBody Product product) {
        if (product.getId() == null || productRepository.findById(product.getId()).isEmpty()) {
            productRepository.save(product);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.findAllByOrderById());
    }

    @PutMapping("product")
    public ResponseEntity<List<Product>> editProduct(@RequestBody Product product) {
        if (productRepository.findById(product.getId()).isPresent()) {
            productRepository.save(product);
        }
        return ResponseEntity.ok().body(productRepository.findAllByOrderById());
    }


    @PatchMapping("add-stock/{id}")
    public ResponseEntity<List<Product>> addStock(@PathVariable Long id) {
        Product product = productRepository.findById(id).get();
        int newStock = product.getStock()+1;
        product.setStock(newStock);
        productRepository.save(product);
        return ResponseEntity.ok().body(productRepository.findAllByOrderById());
    }

    @PatchMapping("decrease-stock/{id}")
    public ResponseEntity<List<Product>> decreaseStock(@PathVariable Long id) {
        Product product = productRepository.findById(id).get();
        if (product.getStock() > 0) {
            int newStock = product.getStock()-1;
            product.setStock(newStock);
            productRepository.save(product);
        }
        return ResponseEntity.ok().body(productRepository.findAllByOrderById());
    }

}
