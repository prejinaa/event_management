package com.example.event.Management.event;

public class EventMapper {
    public static EventDto mapToEventDto( Event event)
    {
        return new EventDto(event.getId(), event.getName(), event.getDate(),
                event.getLocation(), event.getDescription(), event.getOrganizer() );

    }
    public static Event mapToEvent(EventDto eventDto){
        return new Event(eventDto.getId(), eventDto.getName(), eventDto.getDate(),
                eventDto.getLocation(), eventDto.getDescription(), eventDto.getOrganizer() );
    }

}
