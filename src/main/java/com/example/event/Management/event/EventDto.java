package com.example.event.Management.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class EventDto {


   private Long id;
   @NonNull
   private String name;
   @NonNull
   private String date;
   @NotBlank(message = "The location should not blank")
   private String location;
   @NotBlank(message = "the description should not be blank")
   private  String description;
   @NotBlank
   private String organizer;


}
