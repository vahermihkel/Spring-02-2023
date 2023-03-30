package ee.mihkel.webshop.model.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@SequenceGenerator(name="seq",initialValue=1312312321)
public class Order { // Order ja User on reserveeritud PostgreSQL-s
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;

    private Date created;

    @ManyToOne
    private Person person;

//    @ManyToMany
//    private List<Product> orderProducts;

    // Long id;
    // Product product;
    // int quantity;
    @ManyToMany
    private List<CartRow> cartRow;

    private boolean paid;

    private double totalSum;

    private String pmName;

}
