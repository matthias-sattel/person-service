/*
package org.goafabric.personservice.repository.extensions.config;

import org.goafabric.personservice.extensions.HttpInterceptor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "mongodb", matchIfMissing = false)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@RegisterReflectionForBinding(MongoConfiguration.TenantIdBean.class)
public class MongoConfiguration {
    public class TenantIdBean {
        private final String schemaPrefix;
        public TenantIdBean(String schemaPrefix) { this.schemaPrefix = schemaPrefix; }
        public String getPrefix() { return schemaPrefix + HttpInterceptor.getTenantId() + "_"; }
    }
    @Bean
    public TenantIdBean tenantIdBean(@Value("${multi-tenancy.schema-prefix:_}") String schemaPrefix) { return new TenantIdBean(schemaPrefix); }
}

 */