package org.goafabric.personservice.persistence.multitenancy;

import org.goafabric.personservice.persistence.audit.AuditJpaListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditJpaListener.class)
public abstract class AuditAware {
    public abstract String getId();
}
