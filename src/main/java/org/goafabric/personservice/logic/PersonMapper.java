package org.goafabric.personservice.logic;

import org.goafabric.personservice.persistence.domain.PersonBo;
import org.goafabric.personservice.service.dto.Person;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {
    Person map(PersonBo person);

    PersonBo map(Person person);

    List<Person> map(List<PersonBo> countries);
}
