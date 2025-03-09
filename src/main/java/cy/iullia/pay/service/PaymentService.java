package cy.iullia.pay.service;

import cy.iullia.pay.dto.Customer;
import cy.iullia.pay.dto.ErrorPaymentResponse;
import cy.iullia.pay.dto.PaymentRequest;
import cy.iullia.pay.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class PaymentService {
    private final WebClient webClient;

    @Value("${payment.api.token}")
    private String token;

    @Value("${payment.api.base-url}")
    private String baseUrl;

    public PaymentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String createPayment(String amount) {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentType("DEPOSIT")
                .amount(amount)
                .currency("EUR")
                .customer(new Customer())
                .build();
        try {
            PaymentResponse response = webClient.post()
                    .uri(baseUrl + "/payments")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(paymentRequest)
                    .retrieve()
                    .bodyToMono(PaymentResponse.class)
                    .block();

            if (response == null) {
                throw new RuntimeException("Something went wrong, we're working on it");
            }
            return response.getResult().getRedirectUrl();
        } catch (WebClientResponseException e) {
            ErrorPaymentResponse errorResponse = e.getResponseBodyAs(ErrorPaymentResponse.class);
            if (errorResponse == null) {
                throw new RuntimeException("Something went wrong, we're working on it");
            }
            String status = errorResponse.getStatus();
            String error = errorResponse.getError();
            String message;
            if (status.equals("400")) {
                message = errorResponse.getErrors().getFirst().getDefaultMessage();
            } else {
                message = "";
            }
            throw new RuntimeException(status + " " + error + " " + message);
        }
    }
}
