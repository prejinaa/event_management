package com.example.event.Management.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventMapper {

    public static EventDto mapToEventDto( Event event)
    {
        EventDto eventDto=new EventDto(event.getId(), event.getName(), event.getDate(),
                event.getLocation(), event.getDescription(), event.getOrganizer() );
        return eventDto;

    }
    public static Event mapToEvent(EventDto eventDto){
        Event event= new Event(eventDto.getId(), eventDto.getName(), eventDto.getDate(),
                eventDto.getLocation(), eventDto.getDescription(), eventDto.getOrganizer() );
        return event;

    }

}
