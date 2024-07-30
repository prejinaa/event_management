package com.example.event.Management.event;

import com.example.event.Management.exception.EventNotFound;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Slf4j

public class EventServiceImpl implements EventService {
    Logger logger= LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepo eventRepo;

    @Override
    public EventDto createEvent(EventDto eventDto)

    {
        logger.debug("Entering createEvent() with eventDto: {}", eventDto);
        Event event = EventMapper.mapToEvent(eventDto);
        Event createEvent = eventRepo.save(event);
        logger.info("Event created successfully with ID: {}", createEvent.getId());
        return EventMapper.mapToEventDto(createEvent);
    }

    @Override
    public EventDto getById(long id)

    {
        logger.debug("Entering getById() with getId: {}",id);
        Event events = eventRepo.findById(id).orElseThrow(() ->
                new EventNotFound("The event is not exist with this event" + id));
        logger.info("Event with ID {} retrieved successfully.", id);
        return EventMapper.mapToEventDto(events);

    }


    @Override
    public List<EventDto> getEvent()
    {
        logger.debug("Entering getEvent()");
        List<Event> events = eventRepo.findAll();
        logger.info("Retrieved {} events", events.size());
         return events.stream().map((even) -> EventMapper.mapToEventDto(even)).collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(long id)
    {
        logger.debug("Entering deleteEvent() with eventId:{}",id);
        Event event = eventRepo.findById(id).orElseThrow(() ->
                new EventNotFound("Event with this Id " + id + " Not found"));
        logger.info("Event with eventId:{} delete Successfully",id);
        eventRepo.deleteById(id);

    }

    @Override
    public EventDto updateEvent(long id, EventDto eventDto) throws EventNotFound {
        logger.debug("Entering updateEvent() with id: {} and eventDto: {}", id, eventDto);
        Optional<Event> retrieveEvent = eventRepo.findById(id);
        if (retrieveEvent.isEmpty())
        {
            throw new EventNotFound("Book with this id" + id + "not found");
        }

        Event event = retrieveEvent.get();
        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setLocation(eventDto.getLocation());
        event.setOrganizer(eventDto.getOrganizer());
        event = eventRepo.save(event);
        logger.info("Event update with this id:{} successfully",id);
        return EventMapper.mapToEventDto(event);
    }

    @Override
    public Page<EventDto> findAll(int pageNo, int pageSize, String sortBy, String sortDirection)
    {
        logger.debug("Entering findAll() with pageNo: {}, pageSize: {}, sortBy: {}, sortDirection: {}",
                pageNo, pageSize, sortBy, sortDirection);
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Event> page = eventRepo.findAll(pageable);
            logger.info("Retrieve {}:Event with pagination",page.getTotalElements());
            return page.map(EventMapper::mapToEventDto);
    }
}


