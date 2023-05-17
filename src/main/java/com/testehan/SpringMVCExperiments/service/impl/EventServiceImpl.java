package com.testehan.SpringMVCExperiments.service.impl;

import com.testehan.SpringMVCExperiments.dto.EventDTO;
import com.testehan.SpringMVCExperiments.mapper.EventMapper;
import com.testehan.SpringMVCExperiments.model.Club;
import com.testehan.SpringMVCExperiments.model.Event;
import com.testehan.SpringMVCExperiments.repository.ClubRepository;
import com.testehan.SpringMVCExperiments.repository.EventRepository;
import com.testehan.SpringMVCExperiments.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final ClubRepository clubRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(ClubRepository clubRepository, EventRepository eventRepository){
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDTO eventDTO) {
        Club club = clubRepository.findById(clubId).get();
        Event event = EventMapper.mapToEvent(eventDTO);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream().map(event -> EventMapper.mapToEventDTO(event)).collect(Collectors.toList());
    }

    @Override
    public EventDTO findById(Long eventId) {
        return EventMapper.mapToEventDTO(eventRepository.findById(eventId).get());
    }

    @Override
    public void updateEvent(EventDTO eventDTO) {
        Event event = EventMapper.mapToEvent(eventDTO);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(long eventId) {
        eventRepository.deleteById(eventId);
    }
}
