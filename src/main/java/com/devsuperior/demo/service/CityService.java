package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repository.CityRepository;
import com.devsuperior.demo.service.exception.DatabaseException;
import com.devsuperior.demo.service.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<CityDTO> findAllSorted() {
        return repository.findAll(Sort.by("name")).stream().map(city -> new CityDTO(city)).toList();
    }

    public CityDTO saveCity(CityDTO cityDTO) {
        City newCity = new City();
        newCity.setName(cityDTO.getName());

        return new CityDTO(repository.save(newCity));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCity(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ID not found!");
        }

        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade referencial / FK");
        }
    }


}
