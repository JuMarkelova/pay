package cy.iullia.pay.service;

import cy.iullia.pay.dto.Customer;
import cy.iullia.pay.dto.PaymentRequest;
import cy.iullia.pay.dto.PaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayServiceTests {
    @Autowired
    private WebTestClient webTestClient;

    @Value("${payment.api.token}")
    private String token;

    @Value("${payment.api.base-url}")
    private String baseUrl;

    @Test
    void createPaymentSuccessful() {
        PaymentRequest paymentRequest = new PaymentRequest("DEPOSIT", "1000", "EUR", new Customer());

        webTestClient.post()
                .uri(baseUrl + "/payments")
                .header("Authorization", "Bearer " + token)
                .bodyValue(paymentRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(PaymentResponse.class)
                .value(response -> {
                    assertNotNull(response.getResult().getRedirectUrl(), "redirectUrl should not be null");
                    assertFalse(response.getResult().getRedirectUrl().isEmpty(), "redirectUrl should not be empty");
                });
    }

    @Test
    void createPaymentFailureBadRequest() {
        PaymentRequest paymentRequest = new PaymentRequest(
                "DEPOSIT",
                "-10",
                "EUR",
                new Customer());

        webTestClient.post()
                .uri(baseUrl + "/payments")
                .header("Authorization", "Bearer " + token)
                .bodyValue(paymentRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody(String.class)
                .value(response -> assertTrue(response.contains("must be greater than 0")));
    }
}
