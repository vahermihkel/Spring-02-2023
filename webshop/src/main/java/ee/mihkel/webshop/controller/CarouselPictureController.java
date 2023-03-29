package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.CarouselPicture;
import ee.mihkel.webshop.repository.CarouselPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // pääseb muule ligi, ülejäänutel CORS error
public class CarouselPictureController {

    @Autowired
    CarouselPictureRepository carouselPictureRepository;

    @GetMapping("carouselPicture")
    public ResponseEntity<List<CarouselPicture>> getCarouselPicture() {
        return ResponseEntity.ok().body(carouselPictureRepository.findAll());
    }

//    @GetMapping("carouselPicture/{id}")
//    public ResponseEntity<CarouselPicture> getCarouselPicture(@PathVariable Long id) {
//        return ResponseEntity.ok().body(carouselPictureRepository.findById(id).get());
//    }

    @DeleteMapping("carouselPicture/{id}")
    public ResponseEntity<List<CarouselPicture>> deleteCarouselPicture(@PathVariable Long id) {
        carouselPictureRepository.deleteById(id);
        return ResponseEntity.ok().body(carouselPictureRepository.findAll());
    }

    @PostMapping("carouselPicture")
    public ResponseEntity<List<CarouselPicture>> addCarouselPicture(@RequestBody CarouselPicture carouselPicture) {
        if (carouselPicture.getId() == null || carouselPictureRepository.findById(carouselPicture.getId()).isEmpty()) {
            carouselPictureRepository.save(carouselPicture);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(carouselPictureRepository.findAll());
    }

//    @PutMapping("carouselPicture")
//    public ResponseEntity<List<CarouselPicture>> editCarouselPicture(@RequestBody CarouselPicture carouselPicture) {
//        if (carouselPictureRepository.findById(carouselPicture.getId()).isPresent()) {
//            carouselPictureRepository.save(carouselPicture);
//        }
//        return ResponseEntity.ok().body(carouselPictureRepository.findAll());
//    }
}
