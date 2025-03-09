package cy.iullia.pay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentResponse {
    private Result result;

    @Data
    @NoArgsConstructor
    public static class Result {
        private String redirectUrl;
    }
}
