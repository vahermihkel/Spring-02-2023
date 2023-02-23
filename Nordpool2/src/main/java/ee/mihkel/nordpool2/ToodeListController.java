package ee.mihkel.nordpool2;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ToodeListController {
    List<Toode> autod = new ArrayList<>(Arrays.asList(
            new Toode(312311, "BMW", 7.5, true),
            new Toode(312312, "Tesla", 2.5, false),
            new Toode(312313, "Nobe", 5.5, true)
    ));

//    for (int i = 0; i < integers.size(); i++) {
//        sum = sum + integers.get(i);
//    }

//    for (Integer integer : integers) {
//        summa += integer;
//    }

    @GetMapping("hinnad-kokku")
    public double hinnadKokku() {
        double summa = 0;
//      Toode ---> tüüp
//                muutuja  mida läbi käin
        for (Toode toode : autod) {
            summa += toode.getHind();
        }
        return summa;
    }

//        for (int i = 0; i < integers.size(); i++) {
//            int muutuvNr = integers.get(i);
//            muutuvNr += nr;
//            integers.set(i, muutuvNr);
//        }

//    for (int i = 0; i < integers.size(); i++) {
//        integers.set(i, integers.get(i)+num);
//    }

    @GetMapping("suurenda-hinda/{suurendus}")
    public List<Toode> suurendaHinda(@PathVariable double suurendus) {
        for (Toode toode : autod) {
            toode.setHind( toode.getHind() + suurendus );
        }
        return autod;
    }

    // /{dsa}/{}    ?dasd=asdas&hind=23        (@RequestBody)
//  localhost:8080/lisa1/6/BMW/123/true

    @PostMapping("lisa1/{id}/{nimi}/{hind}/{aktiivne}")
    public List<Toode> lisa1(
            @PathVariable int id,
            @PathVariable String nimi,
            @PathVariable double hind,
            @PathVariable boolean aktiivne
    ) {
        Toode toode = new Toode(id, nimi, hind, aktiivne);
        autod.add(toode);
        return autod;
    }

 // localhost:8080/lisa2?id=321&nimi=Audi&hind=23&aktiivne=false
    @PostMapping("lisa2")
    public List<Toode> lisa2(
            @RequestParam int id,
            @RequestParam String nimi,
            @RequestParam double hind,
            @RequestParam boolean aktiivne
    ) {
        Toode toode = new Toode(id, nimi, hind, aktiivne);
        autod.add(toode);
        return autod;
    }

    @PostMapping("lisa3")
    public List<Toode> lisa3(@RequestBody Toode toode) {
//        Toode toode = new Toode(id, nimi, hind, aktiivne);
        autod.add(toode);
        return autod;
    }
}

// 2xx ---> õnnestunud
// 200 OK
// 201 Created -- Loodud

// 4xx ---> front-end viga / Postman / päringu saatja viga
// 400 ---> üldine viga
// 401 ---> sa saaks sisse kui sa annaksid mulle õige kasutajanime ja parooli, aga sa ei andnud
// 402 ---> makse nõutud
// 403 ---> sa ei saa sisse isegi kui annad mulle sisselogimise tunnused
// 404 ---> mitteleitud API otspunkt
// 405 ---> vale meetodi tüüp GET / POST / PUT / DELETE / PATCH
// 415 ---> saadan JSON kuju asemel tekstina

// 5xx ---> serveri viga
// server on maas, anname indexi mis on liiga suur, koodiviga, nullpointer
