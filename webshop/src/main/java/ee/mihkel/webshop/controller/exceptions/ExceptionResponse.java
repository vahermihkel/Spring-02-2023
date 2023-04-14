package ee.mihkel.webshop.controller.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResponse {

    private Date date;
    private String message;
    private int httpStatusCode;
}
