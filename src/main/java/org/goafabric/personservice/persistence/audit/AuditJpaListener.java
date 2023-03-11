package org.goafabric.personservice.persistence.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.goafabric.personservice.persistence.multitenancy.TenantAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;
import java.util.UUID;

public class AuditJpaListener implements ApplicationContextAware {
    private static ApplicationContext context;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private enum DbOperation {
        CREATE, READ, UPDATE, DELETE
    }

    public static record AuditEvent (
            String id,
            String tenantId,
            String referenceId,
            String type,
            DbOperation operation,
            String createdBy,
            Date createdAt,
            String modifiedBy,
            Date   modifiedAt,
            String oldValue,
            String newValue
    ) {}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @PostLoad
    public void afterRead(Object object) {
        afterRead(object, ((TenantAware) object).getId());
    }

    @PostPersist
    public void afterCreate(Object object)  {
        afterCreate(object, ((TenantAware) object).getId());
    }

    @PostUpdate
    public void afterUpdate(Object object) {
        afterUpdate(object, ((TenantAware) object).getId(),
                context.getBean(AuditJpaUpdater.class).findOldObject(object.getClass(), ((TenantAware) object).getId()));
    }

    @PostRemove
    public void afterDelete(Object object) {
        afterDelete(object, ((TenantAware) object).getId());
    }

    public void afterRead(Object object, String id) {
        insertAudit(DbOperation.READ, id, object, object);
    }

    public void afterCreate(Object object, String id) {
        insertAudit(DbOperation.CREATE, id, null, object);
    }

    public void afterUpdate(Object object, String id, Object oldObject) {
        insertAudit(DbOperation.UPDATE, id, oldObject, object);
    }

    public void afterDelete(Object object, String id) {
        insertAudit(DbOperation.DELETE, id, object, null);
    }

    private void insertAudit(final DbOperation operation, String referenceId, final Object oldObject, final Object newObject) {
        try {
            final AuditEvent auditEvent =
                    createAuditEvent(operation, referenceId, oldObject, newObject);
            log.debug("New audit event :\n{}", auditEvent);
            context.getBean(AuditJpaInserter.class).insertAudit(auditEvent, oldObject != null ? oldObject : newObject);
        } catch (Exception e) {
            log.error("Error during audit:\n{}", e.getMessage(), e);
        }
    }

    private AuditEvent createAuditEvent(
            DbOperation dbOperation, String referenceId, final Object oldObject, final Object newObject) throws JsonProcessingException {
        final Date date = new Date(System.currentTimeMillis());
        return new AuditEvent(
                UUID.randomUUID().toString(),
                HttpInterceptor.getTenantId(),
                referenceId,
                newObject.getClass().getSimpleName(),
                dbOperation,
                (dbOperation == DbOperation.CREATE ? HttpInterceptor.getUserName() : null),
                (dbOperation == DbOperation.CREATE ? date : null),
                ((dbOperation == DbOperation.UPDATE || dbOperation == DbOperation.DELETE) ? HttpInterceptor.getUserName() : null),
                ((dbOperation == DbOperation.UPDATE || dbOperation == DbOperation.DELETE) ? date : null),
                (oldObject == null ? null : getJsonValue(oldObject)),
                (newObject == null ? null : getJsonValue(newObject))
        );
    }

    private String getJsonValue(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    @Component
    static class AuditJpaUpdater {
        @PersistenceContext private EntityManager entityManager;

        @Transactional(propagation = Propagation.REQUIRES_NEW) //new transaction helps us to retrieve the old value still inside the db
        public <T> T findOldObject(Class<T> clazz, String id) {
            return entityManager.find(clazz, id);
        }
    }

    @Component
    @RegisterReflectionForBinding(AuditEvent.class)
    static class AuditJpaInserter {
        private DataSource dataSource;

        public AuditJpaInserter(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        public void insertAudit(AuditEvent auditEvent, Object object) { //we cannot use jpa because of the dynamic table name
            new SimpleJdbcInsert(dataSource).withTableName(getTableName(object) + "_audit")
                .execute(new BeanPropertySqlParameterSource(auditEvent));
        }

        private String getTableName(Object object) {
            return object.getClass().getSimpleName().replaceAll("Bo", "").toLowerCase();
        }
    }
}


