package org.goafabric.personservice.persistence.domain;

import jakarta.persistence.*;
import org.goafabric.personservice.persistence.extensions.AuditAware;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TenantId;


@Entity
@Table(name="address")
public class AddressBo extends AuditAware {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String id;

    @TenantId
    public String companyId;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;

    @Override
    public String getId() {
        return id;
    }
}
