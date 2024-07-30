package com.example.event.Management.event;
import com.example.event.Management.exception.EventNotFound;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")

public class EventController {

    Logger logger=LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;
    private final EventRepo eventRepo;

    @PreAuthorize("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/post")
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto eventDto)
    {

        logger.trace("Entering createEvent() with eventDto: {}", eventDto);
        EventDto createEvent = eventService.createEvent(eventDto);

        logger.info("Event created successfully with ID: {}", createEvent.getId());
        logger.trace("Existing createEvent()");

        return new ResponseEntity<>(createEvent, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{eventId}")
    public ResponseEntity<EventDto> getById(@PathVariable("eventId") Long id) throws EventNotFound {
        logger.trace("Entering getById() with getId: {}",id);
        try {
              EventDto event = eventService.getById(id);
              logger.info("Event with ID {} retrieved successfully.", id);
              logger.trace("Existing GetById()");
               return new ResponseEntity<>(event, HttpStatus.OK);
            }
        catch (EventNotFound eventNotFound)
           {
               logger.error("EVent with this Id{} not found:{}",id,eventNotFound.getMessage());
               throw eventNotFound;
           }
    }

    @PreAuthorize(" hasRole('ROLE_ADMIN')" )
    @GetMapping("/")
    public ResponseEntity<List<EventDto>> getEvent() {

        logger.trace("Entering getEvent()");
        List<EventDto> events = eventService.getEvent();
        logger.info("Events is retrieve successfully");
        logger.trace("Existing getEvent");
        return new ResponseEntity<>(events, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        logger.trace("Entering findAll() with pageNo: {}, pageSize: {}, sortBy: {}, sortDirection: {}",
                pageNo, pageSize, sortBy, sortDirection);
        Page<EventDto> page = eventService.findAll(pageNo, pageSize, sortBy, sortDirection);
        logger.info("retrieve:{} event From database",page.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("events", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        logger.trace("Exiting findAll with response:{}",response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("eventId") Long id) throws EventNotFound
    {
        logger.trace("Entering deleteEvent() with eventId:{}",id);
        eventService.deleteEvent(id);
        logger.info("Event with eventId:{} delete Successfully",id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping(value = "/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable("eventId") Long id, @RequestBody EventDto eventDto)
            throws EventNotFound
    {
        logger.trace("Entering UpdateEvent() with eventId:{} and eventDto:{}",id,eventDto);
        EventDto eventDto1 = eventService.updateEvent(id, eventDto);
        logger.info("Event update with this id:{} successfully",id);
        logger.trace("Exiting updateEvent()");
        return new ResponseEntity<>(eventDto1, HttpStatus.OK);


    }
}
