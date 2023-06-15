package org.goafabric.personservice.persistence.extensions;

import org.flywaydb.core.Flyway;
import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

// Source: https://spring.io/blog/2022/07/31/how-to-integrate-hibernates-multitenant-feature-with-spring-data-jpa-in-a-spring-boot-application

@Component
public class TenantResolver implements CurrentTenantIdentifierResolver, MultiTenantConnectionProvider, HibernatePropertiesCustomizer {

    private final DataSource dataSource;

    private final String schemaPrefix;

    private final String defaultSchema;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TenantResolver(DataSource dataSource,
                          @Value("${multi-tenancy.default-schema:PUBLIC}") String defaultSchema,
                          @Value("${multi-tenancy.schema-prefix:_}") String schemaPrefix) {
        this.dataSource = dataSource;
        this.defaultSchema = defaultSchema;
        this.schemaPrefix = schemaPrefix;
    }

    /** Resolver for optional CompanyId via @TenantId Discriminator **/

    @Override
    public String resolveCurrentTenantIdentifier() {
        return HttpInterceptor.getOrgunitId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }

    /** Tenant Resolver for Schema **/

    @Override
    public Connection getConnection(String schema) throws SQLException {
        var connection = dataSource.getConnection();
        connection.setSchema(defaultSchema.equals(schema) ? defaultSchema : schemaPrefix + HttpInterceptor.getTenantId());
        return connection;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection(defaultSchema);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }


    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        connection.setSchema(defaultSchema);
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    /** Flyway configuration to create database schemas **/

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {};
    }

    @Bean
    public CommandLineRunner schemas(Flyway flyway,
                                     @Value("${multi-tenancy.migration.enabled}") Boolean enabled,
                                     @Value("${multi-tenancy.tenants}") String schemas) {
        return args -> {
            if (enabled) {
                Arrays.asList(schemas.split(",")).forEach(schema -> {
                            Flyway.configure()
                                    .configuration(flyway.getConfiguration())
                                    .schemas(schemaPrefix + schema)
                                    .defaultSchema(schemaPrefix + schema)
                                    .placeholders(Map.of("tenantId", schema))
                                    .load()
                                    .migrate();
                        }
                );
            }
        };
    }

}