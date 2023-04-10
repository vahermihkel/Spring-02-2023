package ee.mihkel.webshop.model.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ! User PostgreSQL-s ei saa
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    private String personalCode;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private boolean admin;
}
