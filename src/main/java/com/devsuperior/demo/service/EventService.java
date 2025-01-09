package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repository.CityRepository;
import com.devsuperior.demo.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository repository;
    private final CityRepository cityRepository;

    public EventService(EventRepository repository, CityRepository cityRepository) {
        this.repository = repository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public EventDTO update(Long id, EventDTO eventDTO) {
        if (!repository.existsById(id)) {
            
        }

        Event event = repository.getReferenceById(id);

        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setUrl(eventDTO.getUrl());

        event.setCity(cityRepository.getReferenceById(eventDTO.getCityId()));

        return new EventDTO(event);
    }


}
