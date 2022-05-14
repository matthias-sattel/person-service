package org.goafabric.personservice.persistence.multitenancy;

import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.goafabric.personservice.persistence.audit.AuditJpaListener;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditJpaListener.class)
public abstract class TenantAware {
    public static final String TENANT_FILTER = "TENANT_FILTER";

    @Access(AccessType.PROPERTY)
    public String getTenantId() {return HttpInterceptor.getTenantId();}
    
    public void setTenantId(String tenantId) {}

    public abstract String getId();
}
