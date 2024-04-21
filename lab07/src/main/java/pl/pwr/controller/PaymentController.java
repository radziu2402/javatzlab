package pl.pwr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.entity.Payment;
import pl.pwr.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Pobierz listę wszystkich płatności")
    @ApiResponse(responseCode = "200", description = "Znaleziono płatności",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.findAll();
    }

    @Operation(summary = "Dodaj nową płatność")
    @ApiResponse(responseCode = "201", description = "Płatność została utworzona",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }

    @Operation(summary = "Pobierz płatność po ID")
    @ApiResponse(responseCode = "200", description = "Znaleziono płatność",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @ApiResponse(responseCode = "404", description = "Płatność nie została znaleziona")
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Aktualizuj dane płatności")
    @ApiResponse(responseCode = "200", description = "Dane płatności zostały zaktualizowane",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @ApiResponse(responseCode = "404", description = "Płatność nie została znaleziona")
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment paymentDetails) {
        return paymentService.update(id, paymentDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Usuń płatność")
    @ApiResponse(responseCode = "200", description = "Płatność została usunięta")
    @ApiResponse(responseCode = "404", description = "Płatność nie została znaleziona")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        if (paymentService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
