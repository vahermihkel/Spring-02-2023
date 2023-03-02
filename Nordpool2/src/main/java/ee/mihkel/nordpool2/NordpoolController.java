package ee.mihkel.nordpool2;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class NordpoolController {
    // https://dashboard.elering.ee/api/nps/price

    @GetMapping("nordpool") // localhost:8080/nordpool?country=ee&start=02.03.2023&end=03.03.2023
    public ArrayList<TimestampPrice> getNordpoolPrices(
            @RequestParam String country,
            @RequestParam String start,
            @RequestParam String end) {

        int startYear = Integer.parseInt(start.substring(6,10));
        int startMonth = Integer.parseInt(start.substring(3,5));
        int startDay = Integer.parseInt(start.substring(0,2));
        int endYear = Integer.parseInt(end.substring(6,10));
        int endMonth = Integer.parseInt(end.substring(3,5));
        int endDay = Integer.parseInt(end.substring(0,2));
        // https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute
        // https://stackoverflow.com/questions/36902866/set-the-time-of-day-on-a-zoneddatetime-in-java-time
        // https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
        String startDate = ZonedDateTime.of ( LocalDate.of ( startYear, startMonth, startDay ) , LocalTime.of ( 0 , 0 ) , ZoneId.of ( "Europe/Tallinn" ) ).format( DateTimeFormatter.ISO_INSTANT );
        System.out.println(startDate);

        String endDate = ZonedDateTime.of ( LocalDate.of ( endYear, endMonth, endDay ) , LocalTime.of ( 23 , 59 ) , ZoneId.of ( "Europe/Tallinn" ) ).format( DateTimeFormatter.ISO_INSTANT );
        System.out.println(endDate);

        String url = "https://dashboard.elering.ee/api/nps/price?start="+startDate+"&end="+endDate;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<NordpoolData> response = restTemplate.exchange(url, HttpMethod.GET, null, NordpoolData.class);

//        if (country.equals("ee")) {
//            return response.getBody().getData().getEe();
//        } else if (country.equals("lv")) {
//            return response.getBody().getData().getLv();
//        } else if (country.equals("lt")) {
//            return response.getBody().getData().getLt();
//        } else if (country.equals("fi")) {
//            return response.getBody().getData().getFi();
//        } else {
//            return new ArrayList<>();
//        }

        return switch (country) {
            case "ee" -> response.getBody().getData().getEe();
            case "lv" -> response.getBody().getData().getLv();
            case "lt" -> response.getBody().getData().getLt();
            case "fi" -> response.getBody().getData().getFi();
            default -> new ArrayList<>();
        };
    }

}
