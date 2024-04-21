package pl.pwr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import pl.pwr.entity.Customer;
import pl.pwr.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Pobierz listę wszystkich klientów")
    @ApiResponse(responseCode = "200", description = "Znaleziono klientów",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)))
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @Operation(summary = "Dodaj nowego klienta")
    @ApiResponse(responseCode = "201", description = "Klient został utworzony",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)))
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @Operation(summary = "Pobierz klienta po ID")
    @ApiResponse(responseCode = "200", description = "Znaleziono klienta",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "404", description = "Klient nie został znaleziony")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Aktualizuj dane klienta")
    @ApiResponse(responseCode = "200", description = "Dane klienta zostały zaktualizowane",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)))
    @ApiResponse(responseCode = "404", description = "Klient nie został znaleziony")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        return customerService.update(id, customerDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Usuń klienta")
    @ApiResponse(responseCode = "200", description = "Klient został usunięty")
    @ApiResponse(responseCode = "404", description = "Klient nie został znaleziony")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if (customerService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
