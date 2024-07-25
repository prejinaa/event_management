package com.example.event.Management.event;
import com.example.event.Management.exception.EventNotFound;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


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
    private final EventService eventService;
    private final EventRepo eventRepo;

    @PreAuthorize("hasRole('ROLE_USER')or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/post")
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto eventDto)
    {
        EventDto createEvent = eventService.createEvent(eventDto);
        return new ResponseEntity<>(createEvent, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{eventId}")
    public ResponseEntity<EventDto> getById(@PathVariable("eventId") Long id) throws EventNotFound {
        try {
              EventDto event = eventService.getById(id);
               return new ResponseEntity<>(event, HttpStatus.OK);
            }
        catch (EventNotFound eventNotFound)
           {
            throw eventNotFound;
           }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN)" )
    @GetMapping("/get")
    public ResponseEntity<List<EventDto>> getEvent() {
        List<EventDto> events = eventService.getEvent();
        return new ResponseEntity<>(events, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        Page<EventDto> page = eventService.findAll(pageNo, pageSize, sortBy, sortDirection);

        Map<String, Object> response = new HashMap<>();
        response.put("events", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("eventId") Long id) throws EventNotFound
    {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping(value = "/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable("eventId") Long id, @RequestBody EventDto eventDto)
            throws EventNotFound
    {
        EventDto eventDto1 = eventService.updateEvent(id, eventDto);
        return new ResponseEntity<>(eventDto1, HttpStatus.OK);


    }
}
