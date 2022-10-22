package org.goafabric.personservice.persistence.multitenancy;

import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.stereotype.Component;

@Component
@ImportRuntimeHints(TenantInspector.ApplicationRuntimeHints.class)
public class TenantInspector implements StatementInspector {
    @Override
    public String inspect(String sql) {
        return sql.contains(TenantAware.TENANT_FILTER) ? sql.replace(TenantAware.TENANT_FILTER, "tenant_id = '" + HttpInterceptor.getTenantId() + "'") : sql;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> hibernateProperties.put("hibernate.session_factory.statement_inspector", TenantInspector.class.getName());
    }

    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(TenantInspector.class, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS);
        }
    }
}


