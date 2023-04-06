package org.goafabric.personservice.persistence.multitenancy;

import jakarta.persistence.EntityListeners;

@EntityListeners(AuditListener.class)
public abstract class AuditAware {
    public abstract String getId();
}
