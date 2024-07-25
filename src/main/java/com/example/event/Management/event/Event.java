package com.example.event.Management.event;
import com.example.event.Management.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Event_Info")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EventName")
    private String name;
    @Column(name = "EventDate")
    private String date;
    @Column(name = "EventLocation")
    private String location;
    @Column(name = "Event_Desc")
    private String description;
    @Column(name = "Event_organizer")
    private String organizer;

    @ManyToMany(mappedBy = "events")
    private List<User> users;


    public Event(Long id, String name, String date, String location, String description, String organizer) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.organizer = organizer;
    }
}
