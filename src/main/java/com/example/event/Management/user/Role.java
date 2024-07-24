package com.example.event.Management.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="roles")

public class Role {
    @Getter
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    public String getName() {
        return "ROLE_" + name.toUpperCase();
    }


    // remaining getters and setters
}
