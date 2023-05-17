package com.testehan.SpringMVCExperiments.controller;

import com.testehan.SpringMVCExperiments.dto.EventDTO;
import com.testehan.SpringMVCExperiments.model.Event;
import com.testehan.SpringMVCExperiments.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public String getEventList(Model model)
    {
        List<EventDTO> eventDTOList = eventService.findAllEvents();

        model.addAttribute("events",eventDTOList);

        return "events-list";
    }

    @GetMapping("/events/{clubId}/new")
    public String getEventForm(@PathVariable("clubId") Long clubId, Model model)
    {
        Event event = new Event();
        model.addAttribute("clubId",clubId);
        model.addAttribute("event",event);
        return "events-create";
    }

    @GetMapping("/events/{eventId}")
    public String getEventDetails(@PathVariable("eventId") Long eventId, Model model)
    {
        EventDTO eventDTO = eventService.findById(eventId);
        model.addAttribute("event",eventDTO);

        return "events-detail";
    }

    @PostMapping("/events/{clubId}")
    public String postEvent(@PathVariable("clubId") Long clubId, @Valid @ModelAttribute("event")EventDTO eventDTO,
                            BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("event", eventDTO);
            return "events-create";
        }

        eventService.createEvent(clubId,eventDTO);

        return "redirect:/clubs";
    }

    @GetMapping("/events/{eventId}/edit")
    public String getEditEventForm(@PathVariable("eventId")Long eventId,
                                  Model model)
    {
        EventDTO eventDTO = eventService.findById(eventId);
        model.addAttribute("event", eventDTO);

        return "events-edit";
    }

    @PostMapping("/events/{eventId}/edit")
    public String postEditEventForm(@PathVariable("eventId")Long eventId,
                                    @Valid @ModelAttribute("event") EventDTO eventDTO,  // @Valid will validate object based on annotations from ClubDTO class
                                    BindingResult result, Model model)
    {
        if (result.hasErrors()){
            model.addAttribute("event", eventDTO);
            return "events-edit";
        }

        EventDTO eventDtoFromDB = eventService.findById(eventId);

        eventDTO.setId(eventId);
        eventDTO.setClub(eventDtoFromDB.getClub());

        eventService.updateEvent(eventDTO);

        return "redirect:/events";
    }

    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }
}
