package org.goafabric.personservice.controller.vo;

import java.util.Objects;

public record Address (
        String id,
        String version,
        String street,
        String city) {
    public Address {
        Objects.requireNonNull(street);
        Objects.requireNonNull(city);
    }
}

