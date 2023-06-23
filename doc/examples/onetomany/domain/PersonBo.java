package org.goafabric.personservice.persistence.domain;

import jakarta.persistence.*;
import org.goafabric.personservice.persistence.extensions.AuditListener;
import org.hibernate.annotations.TenantId;

import java.util.List;

@Entity
@Table(name = "person")
public class PersonBo extends AuditListener.AuditAware {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String orgunitId;

    public String firstName;

    public String lastName;

    @OneToMany(mappedBy = "myPerson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<AddressBo> address;

    @Version //optimistic locking
    public Long version;

    @Override
    public String getId() {
        return id;
    }

    public void setAddress(List<AddressBo> myAddress) {
        if (myAddress != null) {
            myAddress.stream().forEach(a -> a.myPerson = this);
            this.address = myAddress;
        }
    }

}
