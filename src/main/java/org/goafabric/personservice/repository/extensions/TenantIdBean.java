package org.goafabric.personservice.repository.extensions;

import org.goafabric.personservice.extensions.HttpInterceptor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Component;

@Component
@RegisterReflectionForBinding(TenantIdBean.class)
public class TenantIdBean {
    public String getPrefix() {
        return "tenant-" + HttpInterceptor.getTenantId() + "-";
    }
}
