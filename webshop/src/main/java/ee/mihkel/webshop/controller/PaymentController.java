package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.CartRow;
import ee.mihkel.webshop.model.request.EverypayResponse;
import ee.mihkel.webshop.model.request.EverypayStatus;
import ee.mihkel.webshop.service.OrderService;
import ee.mihkel.webshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @PostMapping("payment") // MAKSA
    public ResponseEntity<EverypayResponse> makePayment(
//            @PathVariable String personalCode,
            @RequestBody List<CartRow> cartRows) {

        String personalCode = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
// Map<Product, Integer> cartProducts

        String pmName = "";
        Iterator<CartRow> iterator = cartRows.iterator();
        while (iterator.hasNext()) {
            CartRow c = iterator.next();
            if (c.getProduct().getId() == 11110000) {
                pmName = c.getProduct().getName();
                iterator.remove();
                break;
            }
        }
//        cartRows.removeIf(c -> c.getProduct().getId() == 11110000);

        List<CartRow> dbProducts = orderService.getDbProducts(cartRows);

        double totalSum = orderService.calculateTotalSum(dbProducts);

        Long orderId = orderService.saveOrder(pmName, personalCode, dbProducts, totalSum);

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
