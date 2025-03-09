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
        PaymentRequest paymentRequest = new PaymentRequest(
                "DEPOSIT",
                amount,
                "EUR",
                new Customer());
        try {
            PaymentResponse response = webClient.post()
                    .uri(baseUrl + "/payments")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(paymentRequest)
                    .retrieve()
                    .bodyToMono(PaymentResponse.class)
                    .block();

            return response.getResult().getRedirectUrl();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Ошибка API: " + e.getResponseBodyAs(ErrorPaymentResponse.class).getStatus() + " " +
                    e.getResponseBodyAs(ErrorPaymentResponse.class).getError() + " " +
                    e.getResponseBodyAs(ErrorPaymentResponse.class).getErrors().getFirst().getDefaultMessage());
        } catch (Exception e) {
            throw new RuntimeException("Неизвестная ошибка: " + e.getMessage());
        }
    }
}
