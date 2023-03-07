package ee.mihkel.webshop.model.request;

import lombok.Data;

@Data
public class EverypayStatus {
    private String order_reference;
    private String payment_state;
}
