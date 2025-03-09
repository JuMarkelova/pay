package cy.iullia.pay.controller;

import cy.iullia.pay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/")
    public String showPaymentForm() {
        return "payment";
    }

    @PostMapping("/pay")
    public String processPayment(@RequestParam("amount") String amount, Model model) {
        try {
            String redirectUrl = paymentService.createPayment(amount);
            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
