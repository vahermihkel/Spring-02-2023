package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.model.request.EverypayData;
import ee.mihkel.webshop.model.request.EverypayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("payment")
    public EverypayResponse makePayment(@RequestBody List<Product> products) {
        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff"; // tõstame välja application.properties faili

        EverypayData everypayData = new EverypayData();
        everypayData.setApi_username("92ddcfab96e34a5f");
        everypayData.setAccount_name("EUR3D1");
        everypayData.setAmount(100); // teeme arvutuse, mis on kogusumma forEach tsükli + stream
        //  ei võta iga toote juurest summat, mis tuleb Bodyst, vaid tsükli sees pöördun ID-ga andmebaasi poole ja võtan
        //    andmebaasist originaalse toote ja tema küljest hinna
        everypayData.setOrder_reference("dsads1233123"); // Teeme Tellimuste andmebaasimudeli ja sisestama Tellimuse ENNE
        // maksma hakkamist andmebaasi, sest siis saan ID. Märgin et pole makstud
        // Kui makse toimub ära, siis märgin makstuks
        everypayData.setNonce("asdsads" + new Date() + Math.random());
        everypayData.setTimestamp(ZonedDateTime.now().toString());
        everypayData.setCustomer_url("https://err.ee"); // päris serverisse kuhugi üles (Heroku)

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,"Basic OTJkZGNmYWI5NmUzNGE1Zjo4Y2QxOWU5OWU5YzJjMjA4ZWU1NjNhYmY3ZDBlNGRhZA==");

        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(everypayData,headers);

        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST,httpEntity, EverypayResponse.class );

        return response.getBody();
    }

    // PÄRING TULEB AADRESSILE TAGASI NII MAKSTUD KUI MAKSMATA STAATUSEGA
    // Teeme ühe EveryPay päringu veel makse osa lõpetamiseks
    // https://support.every-pay.com/downloads/everypay_apiv4_integration_documentation.pdf
    // 3.6.5. GET /payments/:payment_reference LK 33
}
