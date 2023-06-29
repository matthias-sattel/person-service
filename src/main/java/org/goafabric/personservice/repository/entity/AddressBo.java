package org.goafabric.personservice.repository.entity;

import jakarta.persistence.*;
import org.goafabric.personservice.repository.extensions.AuditListener;
import org.hibernate.annotations.TenantId;


@Entity
@Table(name="address")
public class AddressBo extends AuditListener.AuditAware {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String orgunitId;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;

    @Override
    public String getId() {
        return id;
    }
}
