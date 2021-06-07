package org.goafabric.personservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonBo, String> {
    List<PersonBo> findByFirstName(String firstName);
    List<PersonBo> findByLastName(String lastName);
}

