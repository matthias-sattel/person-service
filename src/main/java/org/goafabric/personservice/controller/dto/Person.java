package org.goafabric.personservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Null
    private String id;
    
    @NotNull
    @Size(min = 3, max = 255)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 255)
    private String lastName;

    private Address address;
}
