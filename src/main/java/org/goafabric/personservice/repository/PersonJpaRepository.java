package org.goafabric.personservice.repository;

import org.goafabric.personservice.repository.entity.PersonEo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonJpaRepository extends CrudRepository<PersonEo, String> {
    List<PersonEo> findByFirstName(String firstName);

    List<PersonEo> findByLastName(String lastName);

    @EntityGraph(attributePaths = "address")
    List<PersonEo> findByAddress_StreetContainsIgnoreCase(String street);

}

