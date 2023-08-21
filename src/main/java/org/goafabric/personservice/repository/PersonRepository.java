package org.goafabric.personservice.repository;

import org.goafabric.personservice.repository.entity.PersonEo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends CrudRepository<PersonEo, String> {
    List<PersonEo> findByFirstName(String firstName);

    List<PersonEo> findByLastName(String lastName);

    @EntityGraph(attributePaths = "address")
    List<PersonEo> findByAddress_City(@Param("city") String city);

}

