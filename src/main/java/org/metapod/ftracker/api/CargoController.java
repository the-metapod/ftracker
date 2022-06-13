package org.metapod.ftracker.api;

import lombok.RequiredArgsConstructor;
import org.metapod.ftracker.exception.DataIntegrityException;
import org.metapod.ftracker.model.dto.CargoDto;
import org.metapod.ftracker.service.CargoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
public class CargoController {
    private final CargoService cargoService;

    @PostMapping
    public ResponseEntity<Void> saveCargo(@RequestBody @Valid List<CargoDto> cargoDtos) throws DataIntegrityException {
        cargoService.save(cargoDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CargoDto>> getAll() {
        return ResponseEntity.ok(cargoService.getAll());
    }
}
