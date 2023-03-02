package ee.mihkel.webshop.controller;

// https://www.omniva.ee/locations.json
// https://www.smartpost.ee/places.json

import ee.mihkel.webshop.model.OmnivaPM;
import ee.mihkel.webshop.model.ParcelMachineResponse;
import ee.mihkel.webshop.model.SmartpostPM;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ParcelMachineController {

    @GetMapping("parcelmachines/{country}")
    public ParcelMachineResponse getParcelMachines(@PathVariable String country) {
        country = country.toUpperCase();

        String omnivaUrl = "https://www.omniva.ee/locations.json";
        String smartpostUrl = "https://www.smartpost.ee/places.json";

        RestTemplate restTemplate = new RestTemplate(); // hiljem: @Autowired

        ResponseEntity<OmnivaPM[]> omnivaResponse =
                restTemplate.exchange(omnivaUrl, HttpMethod.GET, null, OmnivaPM[].class);

        List<OmnivaPM> omniva = new ArrayList<>();
        if (omnivaResponse.getBody() != null) {
            for (OmnivaPM pm: omnivaResponse.getBody()) {
                if (pm.a0_NAME.equals(country)) {
                    omniva.add(pm);
                }
            }
        }

//        List<OmnivaPM> omniva = new ArrayList<>();
//        if (omnivaResponse.getBody() != null) {
//            omniva = List.of(omnivaResponse.getBody());
//            String finalCountry = country;
//            omniva = omniva.stream().filter(e -> e.a0_NAME.equals(finalCountry)).collect(Collectors.toList());
//        }



        ResponseEntity<SmartpostPM[]> smartpostResponse =
                restTemplate.exchange(smartpostUrl, HttpMethod.GET, null, SmartpostPM[].class);

        ParcelMachineResponse response = new ParcelMachineResponse();

        if (country.equals("EE") && smartpostResponse.getBody() != null) {
            List<SmartpostPM> smartpost = List.of(smartpostResponse.getBody());
            response.setSmartpostPMs(smartpost);
        } else {
            response.setSmartpostPMs(new ArrayList<>());
        }

        response.setOmnivaPMs(omniva);


        return response;
    }

}
