package org.goafabric.personservice.repository;

import org.goafabric.personservice.repository.entity.PersonEo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEo, String> {
    List<PersonEo> findByFirstName(String firstName);

    @Query("SELECT p FROM PersonEo p where p.lastName = :lastName")
    List<PersonEo> findByLastName(@Param("lastName") String lastName);
}

