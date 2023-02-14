package ee.mihkel.nordpool2;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ToodeListController {
    List<Toode> autod = new ArrayList<>(Arrays.asList(
            new Toode(312311, "Coca", 7.5, true),
            new Toode(312312, "Sprite", 2.5, false),
            new Toode(312313, "Fanta", 5.5, true)
    ));

    // /{dsa}/{}    ?dasd=asdas&hind=23        (@RequestBody)
}
