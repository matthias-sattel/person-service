package org.goafabric.personservice.repository.entity;

import jakarta.persistence.*;
import org.goafabric.personservice.extensions.AuditTrailListener;
import org.hibernate.annotations.TenantId;

@Entity
@Table(name = "person")
@EntityListeners(AuditTrailListener.class)
public class PersonEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String orgunitId;

    public String firstName;

    public String lastName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressEo address;

    @Version //optimistic locking
    public Long version;

}
