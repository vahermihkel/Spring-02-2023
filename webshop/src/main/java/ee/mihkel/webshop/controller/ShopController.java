package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Shop;
import ee.mihkel.webshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {

    @Autowired
    ShopRepository shopRepository;

    @GetMapping("getshops")
    public ResponseEntity<List<Shop>> getShops() {
        return ResponseEntity.ok().body(shopRepository.findAll());
    }

    @GetMapping("getshop/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        return ResponseEntity.ok().body(shopRepository.findById(id).get());
    }

    @PostMapping("addshop")
    public ResponseEntity<List<Shop>> addShop(@RequestBody Shop shop) {
        if (shop.getId() == null || shopRepository.findById(shop.getId()).isEmpty()) {
            shopRepository.save(shop);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(shopRepository.findAll());
    }

    @DeleteMapping("deleteshop/{id}")
    public ResponseEntity<List<Shop>> deleteShop(@PathVariable Long id) {
        shopRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(shopRepository.findAll());
    }

    @PutMapping("editshop")
    public ResponseEntity<List<Shop>> editShop(@RequestBody Shop shop) {
        if (shopRepository.findById(shop.getId()).isPresent()) {
            shopRepository.save(shop);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(shopRepository.findAll());
    }

}
