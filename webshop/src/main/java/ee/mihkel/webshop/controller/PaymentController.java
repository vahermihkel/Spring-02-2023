package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Order;
import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.model.request.EverypayData;
import ee.mihkel.webshop.model.request.EverypayResponse;
import ee.mihkel.webshop.model.request.EverypayStatus;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.service.OrderService;
import ee.mihkel.webshop.service.PaymentService;
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
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @PostMapping("payment/{personalCode}") // MAKSA
    public ResponseEntity<EverypayResponse> makePayment(
            @PathVariable String personalCode,
            @RequestBody List<Product> products) {

        List<Product> dbProducts = orderService.getDbProducts(products);

        double totalSum = orderService.calculateTotalSum(dbProducts);

        Long orderId = orderService.saveOrder(personalCode, dbProducts, totalSum);

        return ResponseEntity.ok().body(paymentService.getEveryPayLink(totalSum, orderId)); // PAYMENT_LINK ----> Front-end läheb sellele lingile
    }

    // PÄRING TULEB AADRESSILE TAGASI NII MAKSTUD KUI MAKSMATA STAATUSEGA
    // Teeme ühe EveryPay päringu veel makse osa lõpetamiseks
    // https://support.every-pay.com/downloads/everypay_apiv4_integration_documentation.pdf
    // 3.6.5. GET /payments/:payment_reference LK 33

    // https://maksmine.web.app/makse <---- suunab EveryPay
    // Front-end teeb lehele tulemise ajal koheselt päringu check-payment-status/60f8950b4395d
    @PatchMapping("check-payment-status/{paymentReference}")
    public ResponseEntity<EverypayStatus> checkPaymentStatus(@PathVariable String paymentReference) {

        EverypayStatus everypayStatus = paymentService.getPaymentStatus(paymentReference);

        if (everypayStatus != null) {
            if (everypayStatus.getPayment_state().equals("settled")) {
               orderService.changeOrderToPaid(everypayStatus.getOrder_reference());
            }

            everypayStatus.setPayment_state(everypayStatus.getPayment_state());
            everypayStatus.setOrder_reference(everypayStatus.getOrder_reference());
        }


        return ResponseEntity.ok().body(everypayStatus); // NÄITAB FRONT-ENDIS PUNAST VÕI ROHELIST
    }

    // muutust andmebaasis PATCH (paid)
    // ja infot front-endile GET (anna info kas on makstud)
}