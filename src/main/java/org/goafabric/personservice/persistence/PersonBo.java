package org.goafabric.personservice.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity @Table(name = "person")
public class PersonBo {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private String firstName;

    private String lastName;
}
