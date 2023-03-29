package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.CarouselPicture;
import ee.mihkel.webshop.model.database.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarouselPictureRepository extends JpaRepository<CarouselPicture, Long> {
}
