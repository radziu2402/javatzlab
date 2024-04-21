package pl.pwr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.entity.Address;
import pl.pwr.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Pobierz listę wszystkich adresów")
    @ApiResponse(responseCode = "200", description = "Znaleziono adresy",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.findAll();
    }

    @Operation(summary = "Dodaj nowy adres")
    @ApiResponse(responseCode = "201", description = "Adres został utworzony",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressService.save(address);
    }

    @Operation(summary = "Pobierz adres po ID")
    @ApiResponse(responseCode = "200", description = "Znaleziono adres",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    @ApiResponse(responseCode = "404", description = "Adres nie został znaleziony")
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        return addressService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Aktualizuj dane adresu")
    @ApiResponse(responseCode = "200", description = "Dane adresu zostały zaktualizowane",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    @ApiResponse(responseCode = "404", description = "Adres nie został znaleziony")
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        return addressService.findById(id)
                .map(address -> {
                    address.setStreet(addressDetails.getStreet());
                    address.setCity(addressDetails.getCity());
                    address.setPostalCode(addressDetails.getPostalCode());
                    address.setCountry(addressDetails.getCountry());
                    Address updatedAddress = addressService.save(address);
                    return ResponseEntity.ok(updatedAddress);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Usuń adres")
    @ApiResponse(responseCode = "200", description = "Adres został usunięty")
    @ApiResponse(responseCode = "404", description = "Adres nie został znaleziony")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        boolean result = addressService.delete(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
