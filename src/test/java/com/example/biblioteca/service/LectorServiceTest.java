package com.example.biblioteca.service;

import com.example.biblioteca.model.*;
import com.example.biblioteca.repository.CopiaRepository;
import com.example.biblioteca.repository.LectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LectorServiceTest {

    @Mock
    private LectorRepository lectorRepository;

    @Mock
    private CopiaRepository copiaRepository;

    @InjectMocks
    private LectorService lectorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMultar() {
        // Arrange
        Lector lector = new Lector();
        lector.setnSocio(1L);
        lector.setNombre("Juan");
        when(lectorRepository.findById(1L)).thenReturn(Optional.of(lector));

        // Act
        lectorService.multar(1L, 5);

        // Assert
        assertNotNull(lector.getMulta());
        assertEquals(LocalDate.now(), lector.getMulta().getfInicio());
        assertEquals(LocalDate.now().plusDays(10), lector.getMulta().getfFin());
        verify(lectorRepository, times(1)).save(lector);
    }

    @Test
    void testPrestar() {
        // Arrange
        Lector lector = new Lector();
        lector.setnSocio(1L);
        lector.setNombre("Juan");
        lector.setPrestamos(new ArrayList<>());
        when(lectorRepository.findById(1L)).thenReturn(Optional.of(lector));

        Copia copia = new Copia();
        copia.setId(1L);
        copia.setEstado(EstadoCopia.BIBLIOTECA);

        Libro libro = new Libro();
        libro.setId(1L);
        libro.setCopias(new ArrayList<>());
        libro.getCopias().add(copia);

        // Act
        boolean result = lectorService.prestar(1L, libro);

        // Assert
        assertTrue(result);
        assertEquals(EstadoCopia.PRESTADO, copia.getEstado());
        assertEquals(1, lector.getPrestamos().size());
        verify(lectorRepository, times(1)).save(lector);
        verify(copiaRepository, times(1)).save(copia);
    }

    @Test
    void testDevolver() {
        // Arrange
        Lector lector = new Lector();
        lector.setnSocio(1L);
        lector.setNombre("Juan");

        Copia copia = new Copia();
        copia.setId(1L);
        copia.setEstado(EstadoCopia.PRESTADO);

        Prestamo prestamo = new Prestamo(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(30)), copia, lector);
        lector.setPrestamos(new ArrayList<>());
        lector.getPrestamos().add(prestamo);

        when(lectorRepository.findById(1L)).thenReturn(Optional.of(lector));

        // Act
        boolean result = lectorService.devolver(1L, copia);

        // Assert
        assertTrue(result);
        assertEquals(EstadoCopia.BIBLIOTECA, copia.getEstado());
        assertTrue(lector.getPrestamos().isEmpty());
        verify(lectorRepository, times(1)).save(lector);
        verify(copiaRepository, times(1)).save(copia);
    }
}