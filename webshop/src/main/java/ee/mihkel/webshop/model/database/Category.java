package ee.mihkel.webshop.model.database;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

//    @OneToMany
//    private List<Product> productList;
}

// product 1, 2
// category 3, 4
// product 5,

// product 1,2
// category 1,2,3,4
// product 3,4,5,6,7
// category 5
