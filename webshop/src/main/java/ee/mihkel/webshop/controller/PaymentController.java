package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Order;
import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.model.request.EverypayData;
import ee.mihkel.webshop.model.request.EverypayResponse;
import ee.mihkel.webshop.model.request.EverypayStatus;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    OrderRepository orderRepository;

    @Value("${everypay.url}") // import mitte lombokist
    private String everypayUrl;

    @Value("${everypay.username}") // import mitte lombokist
    private String username;

    @Value("${everypay.account}") // import mitte lombokist
    private String account;

    @Value("${everypay.authorization}") // import mitte lombokist
    private String authorization;

    @Value("${everypay.customerUrl}") // import mitte lombokist
    private String customerUrl;

    @PostMapping("payment/{personalCode}") // MAKSA
    public EverypayResponse makePayment(
            @PathVariable String personalCode,
            @RequestBody List<Product> products) {

        Person person = personRepository.findById(personalCode).get();

        Order order = new Order();
        order.setOrderProducts(products);
        order.setPaid(false);
        order.setPerson(person);
        order.setCreated(new Date());
        order.setTotalSum(123);
        Order dbOrder = orderRepository.save(order);

        String url = everypayUrl + "payments/oneoff";

        EverypayData everypayData = new EverypayData();
        everypayData.setApi_username(username);
        everypayData.setAccount_name(account);
        everypayData.setAmount(123); // teeme arvutuse, mis on kogusumma forEach ts??kli + stream
        //  ei v??ta iga toote juurest summat, mis tuleb Bodyst, vaid ts??kli sees p????rdun ID-ga andmebaasi poole ja v??tan
        //    andmebaasist originaalse toote ja tema k??ljest hinna
        everypayData.setOrder_reference(dbOrder.getId().toString()); // Teeme Tellimuste andmebaasimudeli ja sisestama Tellimuse ENNE
        // maksma hakkamist andmebaasi, sest siis saan ID. M??rgin et pole makstud
        // Kui makse toimub ??ra, siis m??rgin makstuks
        everypayData.setNonce("asdsads" + new Date() + Math.random());
        everypayData.setTimestamp(ZonedDateTime.now().toString());
        everypayData.setCustomer_url(customerUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,authorization);
        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(everypayData,headers);

        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST,httpEntity, EverypayResponse.class );

        return response.getBody(); // PAYMENT_LINK ----> Front-end l??heb sellele lingile
    }

    // P??RING TULEB AADRESSILE TAGASI NII MAKSTUD KUI MAKSMATA STAATUSEGA
    // Teeme ??he EveryPay p??ringu veel makse osa l??petamiseks
    // https://support.every-pay.com/downloads/everypay_apiv4_integration_documentation.pdf
    // 3.6.5. GET /payments/:payment_reference LK 33

    // https://maksmine.web.app/makse <---- suunab EveryPay
    // Front-end teeb lehele tulemise ajal koheselt p??ringu check-payment-status/60f8950b4395d
    @PatchMapping("check-payment-status/{paymentReference}")
    public EverypayStatus checkPaymentStatus(@PathVariable String paymentReference) {
        // /payments/d2b859a4d60f8950b4395dcaecfdffe0720ce2a1113d66abd5c89de56eaa45dd?api_username=92ddcfab96e34a5f
        // "https://igw-demo.every-pay.com/api/v4/payments/d2b859a4d60f8950b4395dcaecfdffe0720ce2a1113d66abd5c89de56eaa45dd?api_username=92ddcfab96e34a5f"
        String url = everypayUrl + "payments/" + paymentReference + "?api_username=" + username;

        EverypayStatus everypayStatus = new EverypayStatus();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, authorization);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<EverypayStatus> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity, EverypayStatus.class);

        EverypayStatus body = response.getBody();

        if (body != null) {
            if (body.getPayment_state().equals("settled")) {
               Long orderId = Long.parseLong(body.getOrder_reference());
               Order order = orderRepository.findById(orderId).get();
               order.setPaid(true);
               orderRepository.save(order);
            }

            everypayStatus.setPayment_state(body.getPayment_state());
            everypayStatus.setOrder_reference(body.getOrder_reference());
        }


        return everypayStatus; // N??ITAB FRONT-ENDIS PUNAST V??I ROHELIST
    }

    // muutust andmebaasis PATCH (paid)
    // ja infot front-endile GET (anna info kas on makstud)
}
