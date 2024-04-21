package pl.pwr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import pl.pwr.entity.Charge;
import pl.pwr.service.ChargeService;

import java.util.List;

@RestController
@RequestMapping("/api/charges")
public class ChargeController {

    private final ChargeService chargeService;

    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @Operation(summary = "Pobierz listę wszystkich naliczonych należności")
    @ApiResponse(responseCode = "200", description = "Znaleziono należności",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Charge.class)))
    @GetMapping
    public List<Charge> getAllCharges() {
        return chargeService.findAll();
    }

    @Operation(summary = "Dodaj nową należność")
    @ApiResponse(responseCode = "201", description = "Należność została utworzona",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Charge.class)))
    @PostMapping
    public Charge createCharge(@RequestBody Charge charge) {
        return chargeService.save(charge);
    }

    @Operation(summary = "Pobierz należność po ID")
    @ApiResponse(responseCode = "200", description = "Znaleziono należność",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Charge.class)))
    @GetMapping("/{id}")
    public ResponseEntity<Charge> getChargeById(@PathVariable Long id) {
        return chargeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Aktualizuj dane należności")
    @ApiResponse(responseCode = "200", description = "Dane należności zostały zaktualizowane",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Charge.class)))
    @PutMapping("/{id}")
    public ResponseEntity<Charge> updateCharge(@PathVariable Long id, @RequestBody Charge chargeDetails) {
        return chargeService.update(id, chargeDetails);
    }

    @Operation(summary = "Usuń należność")
    @ApiResponse(responseCode = "200", description = "Należność została usunięta")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCharge(@PathVariable Long id) {
        return chargeService.delete(id);
    }

    @Operation(summary = "Usuń wszystkie naliczone należności")
    @ApiResponse(responseCode = "200", description = "Wszystkie naliczone należności zostały usunięte")
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllCharges() {
        chargeService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
