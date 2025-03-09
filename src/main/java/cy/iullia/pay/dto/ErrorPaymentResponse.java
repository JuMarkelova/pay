package cy.iullia.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorPaymentResponse {
    private String status;
    private String error;
    private List<Error> errors;

    @Data
    @NoArgsConstructor
    public static class Error {
        String defaultMessage;
    }
}
