package ee.mihkel.webshop.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ParcelMachineResponse {
    private List<OmnivaPM> omnivaPMs;
    private List<SmartpostPM> smartpostPMs;
}
