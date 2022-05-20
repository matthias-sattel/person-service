package org.goafabric.personservice.persistence.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.goafabric.personservice.persistence.multitenancy.TenantAware;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Version;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
