package ee.mihkel.webshop.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class EverypayData {
    private String api_username;
    private String account_name;
    private double amount;
    private String order_reference;
    private String nonce;
    private String timestamp;
    private String customer_url;
}
