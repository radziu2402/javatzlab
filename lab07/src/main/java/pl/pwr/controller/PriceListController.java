package pl.pwr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.entity.PriceList;
import pl.pwr.service.PriceListService;

import java.util.List;

@RestController
@RequestMapping("/api/pricelists")
public class PriceListController {

    private final PriceListService priceListService;

    public PriceListController(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    @Operation(summary = "Pobierz listę wszystkich cenników")
    @ApiResponse(responseCode = "200", description = "Znaleziono cenniki",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceList.class)))
    @GetMapping
    public List<PriceList> getAllPriceLists() {
        return priceListService.findAll();
    }

    @Operation(summary = "Dodaj nowy cennik")
    @ApiResponse(responseCode = "201", description = "Cennik został utworzony",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceList.class)))
    @PostMapping
    public PriceList createPriceList(@RequestBody PriceList priceList) {
        return priceListService.save(priceList);
    }

    @Operation(summary = "Pobierz cennik po ID")
    @ApiResponse(responseCode = "200", description = "Znaleziono cennik",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceList.class)))
    @GetMapping("/{id}")
    public ResponseEntity<PriceList> getPriceListById(@PathVariable Long id) {
        return priceListService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Aktualizuj cennik")
    @ApiResponse(responseCode = "200", description = "Cennik został zaktualizowany",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceList.class)))
    @PutMapping("/{id}")
    public ResponseEntity<PriceList> updatePriceList(@PathVariable Long id, @RequestBody PriceList priceListDetails) {
        return priceListService.update(id, priceListDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Usuń cennik")
    @ApiResponse(responseCode = "200", description = "Cennik został usunięty",
            content = @Content(mediaType = "application/json"))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePriceList(@PathVariable Long id) {
        if (priceListService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
