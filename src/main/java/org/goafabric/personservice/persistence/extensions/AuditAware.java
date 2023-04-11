package org.goafabric.personservice.persistence.extensions;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditAware {
    public abstract String getId();
}
