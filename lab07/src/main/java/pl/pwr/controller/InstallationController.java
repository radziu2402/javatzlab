package pl.pwr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.entity.Installation;
import pl.pwr.service.InstallationService;

import java.util.List;

@RestController
@RequestMapping("/api/installations")
public class InstallationController {

    private final InstallationService installationService;

    public InstallationController(InstallationService installationService) {
        this.installationService = installationService;
    }

    @Operation(summary = "Pobierz listę wszystkich instalacji")
    @ApiResponse(responseCode = "200", description = "Znaleziono instalacje",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Installation.class)))
    @GetMapping
    public List<Installation> getAllInstallations() {
        return installationService.findAll();
    }

    @Operation(summary = "Dodaj nową instalację", description = "Tworzy nową instalację. Adres instalacji musi być unikalny.")
    @ApiResponse(responseCode = "201", description = "Instalacja została utworzona",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Installation.class)))
    @PostMapping
    public Installation createInstallation(@RequestBody Installation installation) {
        return installationService.save(installation);
    }

    @Operation(summary = "Pobierz instalację po ID")
    @ApiResponse(responseCode = "200", description = "Znaleziono instalację",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Installation.class)))
    @ApiResponse(responseCode = "404", description = "Instalacja nie została znaleziona")
    @GetMapping("/{id}")
    public ResponseEntity<Installation> getInstallationById(@PathVariable Long id) {
        return installationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Aktualizuj dane instalacji")
    @ApiResponse(responseCode = "200", description = "Dane instalacji zostały zaktualizowane",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Installation.class)))
    @ApiResponse(responseCode = "404", description = "Instalacja nie została znaleziona")
    @PutMapping("/{id}")
    public ResponseEntity<Installation> updateInstallation(@PathVariable Long id, @RequestBody Installation installationDetails) {
        return installationService.update(id, installationDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Usuń instalację")
    @ApiResponse(responseCode = "200", description = "Instalacja została usunięta")
    @ApiResponse(responseCode = "404", description = "Instalacja nie została znaleziona")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstallation(@PathVariable Long id) {
        if (installationService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
