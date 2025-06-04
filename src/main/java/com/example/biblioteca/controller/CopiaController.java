package com.example.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.biblioteca.model.Copia;
import com.example.biblioteca.service.CopiaService;

@RestController
@RequestMapping("/copias")
public class CopiaController {

    private final CopiaService service;

    @Autowired
    public CopiaController(CopiaService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Copia> getAllCopias() {
        return service.getAllCopias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Copia> getCopiaById(@PathVariable Long id) {
        Optional<Copia> copia = service.getCopiaById(id);
        return copia.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Copia> createCopia(@RequestBody Copia copia) {
        Copia createdCopia = service.createCopia(copia);
        return ResponseEntity.ok(createdCopia);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Copia> updateCopia(@PathVariable Long id, @RequestBody Copia copiaDetails) {
        Optional<Copia> updatedCopia = service.updateCopia(id, copiaDetails);
        return updatedCopia.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCopia(@PathVariable Long id) {
        boolean deleted = service.deleteCopia(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
