package org.goafabric.personservice.repository.entity;

import jakarta.persistence.*;
import org.goafabric.personservice.repository.extensions.AuditTrailListener;


@Entity
@Table(name="address")
@EntityListeners(AuditTrailListener.class)
public class AddressEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;
}
