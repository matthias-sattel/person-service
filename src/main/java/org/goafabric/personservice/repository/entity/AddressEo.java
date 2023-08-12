package org.goafabric.personservice.repository.entity;

import jakarta.persistence.*;
import org.goafabric.personservice.repository.extensions.AuditTrailListener;
import org.hibernate.annotations.TenantId;


@Entity
@Table(name="address")
@EntityListeners(AuditTrailListener.class)
public class AddressEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String personId;

    @TenantId
    public String orgunitId;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;
}
