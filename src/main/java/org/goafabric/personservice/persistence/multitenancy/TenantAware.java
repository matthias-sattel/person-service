package org.goafabric.personservice.persistence.multitenancy;

import org.goafabric.personservice.crossfunctional.HttpInterceptor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class TenantAware extends AuditAware{
    public static final String TENANT_FILTER = "TENANT_FILTER";

    @Access(AccessType.PROPERTY)
    public String getTenantId() {return HttpInterceptor.getTenantId();}
    
    public void setTenantId(String tenantId) {}

}
