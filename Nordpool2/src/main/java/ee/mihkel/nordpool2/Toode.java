package ee.mihkel.nordpool2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Toode {
    private int id;
    private String nimi;   // String --> char   's'   "Sdaweqw"
    private double hind;   // float 4bit, double 8bit
    private boolean aktiivne;  // "y" "n"    0  1      "s"  2

//    public Toode (String nimi, double hind) {}
}
