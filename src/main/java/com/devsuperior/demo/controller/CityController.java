package com.devsuperior.demo.controller;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> findAllCities() {
        return ResponseEntity.ok(service.findAllSorted());
    }

    @PostMapping
    public ResponseEntity<CityDTO> saveCity(@RequestBody CityDTO cityDTO) {
        CityDTO newCity = service.saveCity(cityDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(newCity.getId()).toUri();

        return ResponseEntity.created(uri).body(service.saveCity(cityDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        service.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
