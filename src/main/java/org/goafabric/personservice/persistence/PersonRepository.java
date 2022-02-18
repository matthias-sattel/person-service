package org.goafabric.personservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonBo, String> {
    List<PersonBo> findByFirstName(String firstName);

    @Query("SELECT p FROM PersonBo p where p.lastName = :lastName")
    List<PersonBo> findByLastName(@Param("lastName") String lastName);
}

