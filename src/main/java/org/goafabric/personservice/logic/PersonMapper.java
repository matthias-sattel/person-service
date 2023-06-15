package org.goafabric.personservice.logic;

import org.goafabric.personservice.persistence.domain.PersonBo;
import org.goafabric.personservice.controller.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {
    Person map(PersonBo value);

    PersonBo map(Person value);

    List<Person> map(List<PersonBo> value);
}
