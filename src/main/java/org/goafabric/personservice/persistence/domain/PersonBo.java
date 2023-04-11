package org.goafabric.personservice.persistence.domain;

import jakarta.persistence.*;
import org.goafabric.personservice.persistence.extensions.AuditListener;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TenantId;

@Entity
@Table(name = "person")
public class PersonBo extends AuditListener.AuditAware {
    @Id
    @GeneratedValue(generator = "uuid") @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String id;

    @TenantId
    public String companyId;

    public String firstName;

    public String lastName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressBo address;

    @Version //optimistic locking
    public Long version;

    @Override
    public String getId() {
        return id;
    }
}
