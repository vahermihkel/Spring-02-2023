package ee.mihkel.nordpool2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToodeController {
    // {"id": 312312, "name": "Coca", "hind": 7.5, ....}
    Toode toode = new Toode(312312, "Coca", 7.5, true);

    @GetMapping("saa-toode")
    public Toode saaToode () {
        return toode;
    }


}
