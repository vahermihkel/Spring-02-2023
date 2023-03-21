package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.request.EverypayData;
import ee.mihkel.webshop.model.request.EverypayResponse;
import ee.mihkel.webshop.model.request.EverypayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class PaymentService {

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

    @Autowired
    RestTemplate restTemplate;

    public EverypayResponse getEveryPayLink(double totalSum, Long orderId) {
        String url = everypayUrl + "payments/oneoff";

        EverypayData everypayData = getEverypayData(totalSum, orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,authorization);
        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(everypayData,headers);

        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST,httpEntity, EverypayResponse.class );
        return response.getBody();
    }

    private EverypayData getEverypayData(double totalSum, Long orderId) {
        EverypayData everypayData = new EverypayData();
        everypayData.setApi_username(username);
        everypayData.setAccount_name(account);
        everypayData.setAmount(totalSum);
        everypayData.setOrder_reference(orderId.toString());
        everypayData.setNonce("asdsads" + new Date() + Math.random());
        everypayData.setTimestamp(ZonedDateTime.now().toString());
        everypayData.setCustomer_url(customerUrl);
        return everypayData;
    }

    public EverypayStatus getPaymentStatus(String paymentReference) {

        // /payments/d2b859a4d60f8950b4395dcaecfdffe0720ce2a1113d66abd5c89de56eaa45dd?api_username=92ddcfab96e34a5f
        // "https://igw-demo.every-pay.com/api/v4/payments/d2b859a4d60f8950b4395dcaecfdffe0720ce2a1113d66abd5c89de56eaa45dd?api_username=92ddcfab96e34a5f"
        String url = everypayUrl + "payments/" + paymentReference + "?api_username=" + username;

        EverypayStatus everypayStatus = new EverypayStatus();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, authorization);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<EverypayStatus> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity, EverypayStatus.class);

        return response.getBody();
    }
}
