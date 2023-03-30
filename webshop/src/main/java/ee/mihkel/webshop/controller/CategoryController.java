package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Category;
import ee.mihkel.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200") // pääseb muule ligi, ülejäänutel CORS error
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("category")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @GetMapping("category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryRepository.findById(id).get());
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @PostMapping("category")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) {
        if (category.getId() == null || categoryRepository.findById(category.getId()).isEmpty()) {
            categoryRepository.save(category);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.findAll());
    }

    @PutMapping("category")
    public ResponseEntity<List<Category>> editCategory(@RequestBody Category category) {
        if (categoryRepository.findById(category.getId()).isPresent()) {
            categoryRepository.save(category);
        }
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }
}
