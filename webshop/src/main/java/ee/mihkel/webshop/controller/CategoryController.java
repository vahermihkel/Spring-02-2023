package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.Category;
import ee.mihkel.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("category")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("category/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).get();
    }

    @DeleteMapping("category/{id}")
    public List<Category> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }

    @PostMapping("category")
    public List<Category> addCategory(@RequestBody Category category) {
        if (category.getId() == null || categoryRepository.findById(category.getId()).isEmpty()) {
            categoryRepository.save(category);
        }
        return categoryRepository.findAll();
    }

    @PutMapping("category")
    public List<Category> editCategory(@RequestBody Category category) {
        if (categoryRepository.findById(category.getId()).isPresent()) {
            categoryRepository.save(category);
        }
        return categoryRepository.findAll();
    }
}
