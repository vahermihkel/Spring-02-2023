package ee.mihkel.webshop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private double price;
    private String image;
    private boolean active;

    // private Category category;
}
