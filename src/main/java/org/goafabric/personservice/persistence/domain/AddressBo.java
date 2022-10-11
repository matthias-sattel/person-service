package org.goafabric.personservice.persistence.domain;

import jakarta.persistence.*;
import lombok.*;
import org.goafabric.personservice.persistence.multitenancy.TenantAware;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="address")
@Where(clause = TenantAware.TENANT_FILTER)
public class AddressBo extends TenantAware {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String street;
    private String city;

    @Version //optimistic locking
    private Long version;
}
