package org.goafabric.personservice.persistence.multitenancy;

import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.goafabric.audit.AuditJpaListener;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditJpaListener.class)
public abstract class TenantAware {
    public static final String TENANT_FILTER = "TENANT_FILTER";

    @Access(AccessType.PROPERTY)
    public String getTenantId() {return HttpInterceptor.getTenantId();}
    
    public void setTenantId(String tenantId) {}

    public abstract String getId();
}
