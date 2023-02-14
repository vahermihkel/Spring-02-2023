package ee.mihkel.nordpool2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StringListController {
    List<String> autod = new ArrayList<>(Arrays.asList("BMW", "Audi", "Tesla"));

    @GetMapping("saa-autod")
    public List<String> saaAutod() {
        return autod;
    }

    // DeleteMapping
    @GetMapping("kustuta-auto/{index}")
    // void, String ("kustutamine õnnestus"), List
    public List<String> kustutaAuto(@PathVariable int index) {
        autod.remove(index);
        return autod;
    }

    // DeleteMapping
    @GetMapping("tyhjenda")
    // void, String ("kustutamine õnnestus"), List
    public List<String> tyhjenda() {
        autod.clear();
        return autod;
    }

    @GetMapping("vaata-autot/{index}")
    public String vaataAutot(@PathVariable int index) {
        return autod.get(index);
    }

    // PostMapping
    @GetMapping("lisa/{auto}")
    public List<String> lisaAuto(@PathVariable String auto) {
        autod.add(auto);
        return autod;
    }

    // PutMapping
    @GetMapping("muuda/{index}/{auto}")
    public List<String> muudaAuto(@PathVariable int index, @PathVariable String auto) {
        autod.set(index, auto);
        return autod;
    }

}
