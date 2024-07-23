package com.example.event.Management.event;

import com.example.event.Management.exception.EventNotFound;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;


    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = EventMapper.mapToEvent(eventDto);

        Event createEvent = eventRepo.save(event);
        return EventMapper.mapToEventDto(createEvent);
    }

    @Override
    public EventDto getById(long id) {
        Event events = eventRepo.findById(id).orElseThrow(() ->
                new EventNotFound("The event is not exist with this event" + id));
        return EventMapper.mapToEventDto(events);

    }


    @Override
    public List<EventDto> getEvent() {
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
//        Pageable pageable=PageRequest.of(pageNo,pageSize);
//        Page<Event>page=eventRepo.findAll(pageable);

        List<Event> events = eventRepo.findAll();
         return events.stream().map((even) -> EventMapper.mapToEventDto(even)).collect(Collectors.toList());
       // return page.getContent().stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());

    }

    @Override
    public void deleteEvent(long id) {
        Event event = eventRepo.findById(id).orElseThrow(()
                -> new EventNotFound("Event with this Id " + id + " Not found"));

        eventRepo.deleteById(id);
        ;


    }

    @Override
    public EventDto updateEvent(long id, EventDto eventDto) throws EventNotFound {
        Optional<Event> retrieveEvent = eventRepo.findById(id);
        if (retrieveEvent.isEmpty()) {
            throw new EventNotFound("Book with this id" + id + "not found");
        }

        Event event = retrieveEvent.get();
        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setLocation(eventDto.getLocation());
        event.setOrganizer(eventDto.getOrganizer());
        event = eventRepo.save(event);
        return EventMapper.mapToEventDto(event);
    }

    @Override


    public Page<EventDto> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
            Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Event> page = eventRepo.findAll(pageable);
            return page.map(EventMapper::mapToEventDto);
        }
    }


