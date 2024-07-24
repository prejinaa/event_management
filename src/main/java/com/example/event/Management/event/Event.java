package com.example.event.Management.event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Event_Info")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="EventName")
    private String name;
    @Column(name="EventDate")
    private String date;
    @Column(name="EventLocation")
    private String location;
    @Column(name="Event_Desc")
    private  String  description;
    @Column(name="Event_organizer")
    private String organizer;






}
