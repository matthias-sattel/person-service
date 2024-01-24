package org.goafabric.personservice.repository;

import org.goafabric.personservice.repository.entity.PersonEo;
import org.goafabric.personservice.controller.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {
    Person map(PersonEo value);

    PersonEo map(Person value);

    List<Person> map(List<PersonEo> value);

    List<Person> map(Iterable<PersonEo> value);
}
