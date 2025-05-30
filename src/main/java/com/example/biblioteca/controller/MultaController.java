package com.example.biblioteca.controller;

import com.example.biblioteca.model.Multa;
import com.example.biblioteca.service.MultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/multas")
public class MultaController {

    private final MultaService multaService;

    public MultaController(MultaService multaService) {
        this.multaService = multaService;
    }

    @GetMapping
    public List<Multa> getAllMultas() {
        return multaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multa> getMultaById(@PathVariable Long id) {
        return multaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Multa createMulta(@RequestBody Multa multa) {
        return multaService.save(multa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Multa> updateMulta(@PathVariable Long id, @RequestBody Multa multaDetails) {
        return multaService.findById(id)
                .map(multa -> {
                    multa.setfInicio(multaDetails.getfInicio());
                    multa.setfFin(multaDetails.getfFin());
                    Multa updated = multaService.save(multa);
                    return ResponseEntity.ok(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMulta(@PathVariable Long id) {
        if (multaService.findById(id).isPresent()) {
            multaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

