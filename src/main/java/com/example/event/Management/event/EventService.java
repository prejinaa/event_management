package com.example.event.Management.event;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface EventService {

    EventDto createEvent(EventDto eventDto);

    EventDto getById(long id);

    List<EventDto> getEvent();

    void deleteEvent(long id);

    EventDto updateEvent(long id, EventDto eventDto);

    Page<EventDto> findAll(int pageNo, int pageSize, String sortBy, String sortDirection);
}

