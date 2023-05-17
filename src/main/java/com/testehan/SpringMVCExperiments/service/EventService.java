package com.testehan.SpringMVCExperiments.service;

import com.testehan.SpringMVCExperiments.dto.EventDTO;

import java.util.List;

public interface EventService {

    void createEvent(Long clubId, EventDTO eventDTO);

    List<EventDTO> findAllEvents();

    EventDTO findById(Long eventId);

    void updateEvent(EventDTO eventDTO);

    void deleteEvent(long eventId);
}
