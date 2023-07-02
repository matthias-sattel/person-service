package org.goafabric.personservice.repository.entity;

import jakarta.persistence.*;
import org.goafabric.personservice.repository.extensions.AuditTrail;
import org.hibernate.annotations.TenantId;


@Entity
@Table(name="address")
@EntityListeners(AuditTrail.class)
public class AddressEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String orgunitId;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;
}
