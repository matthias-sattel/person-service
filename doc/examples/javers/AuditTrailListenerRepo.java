package org.goafabric.personservice.repository.extensions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import org.goafabric.personservice.extensions.HttpInterceptor;
import org.hibernate.annotations.TenantId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

// Simple Audittrail that fulfills the requirements of logging content changes + user + aot support, could be db independant
public class AuditTrailListenerRepo implements ApplicationContextAware {
    private static ApplicationContext context;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private enum DbOperation { CREATE, READ, UPDATE, DELETE }

    @Entity
    @Table(name = "audit_trail")
    class AuditTrail {
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        String id;
        @TenantId
        String orgunitId;
        String objectType;
        String objectId;
        DbOperation operation;
        String createdBy;
        Date createdAt;
        String modifiedBy;
        Date modifiedAt;
        String oldValue;
        String newValue;
    }

    interface AuditTrailRepository extends CrudRepository<AuditTrail, String> {
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        AuditTrail save(AuditTrail auditTrail);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @PostPersist
    public void afterCreate(Object object)  {
        insertAudit(DbOperation.CREATE, getId(object), null, object);
    }

    @PostUpdate
    public void afterUpdate(Object object) {
        final String id = getId(object);
        insertAudit(DbOperation.UPDATE, id,
                context.getBean(AuditJpaUpdater.class).findOldObject(object.getClass(), id), object);
    }

    @PostRemove
    public void afterDelete(Object object) {
        insertAudit(DbOperation.DELETE, getId(object), object, null);
    }

    private void insertAudit(final AuditTrailListenerRepo.DbOperation operation, String referenceId, final Object oldObject, final Object newObject) {
        try {
            var auditTrail = createAuditTrail(operation, referenceId, oldObject, newObject);
            log.debug("New audit:\n{}", auditTrail);
            context.getBean(AuditTrailRepository.class).save(auditTrail);
        } catch (Exception e) {
            log.error("Error during audit:\n{}", e.getMessage(), e);
        }
    }

    private AuditTrail createAuditTrail(
            DbOperation dbOperation, String referenceId, final Object oldObject, final Object newObject) throws JsonProcessingException {
        final Date date = new Date(System.currentTimeMillis());
        var auditTrail = new AuditTrail();
        auditTrail.objectType = getTableName(newObject != null ? newObject : oldObject);
        auditTrail.objectId = referenceId;
        auditTrail.operation = dbOperation;
        auditTrail.createdBy = (dbOperation == AuditTrailListenerRepo.DbOperation.CREATE ? HttpInterceptor.getUserName() : null);
        auditTrail.createdAt = (dbOperation == AuditTrailListenerRepo.DbOperation.CREATE ? date : null);
        auditTrail.modifiedBy = ((dbOperation == AuditTrailListenerRepo.DbOperation.UPDATE || dbOperation == AuditTrailListenerRepo.DbOperation.DELETE) ? HttpInterceptor.getUserName() : null);
        auditTrail.modifiedAt = ((dbOperation == AuditTrailListenerRepo.DbOperation.UPDATE || dbOperation == AuditTrailListenerRepo.DbOperation.DELETE) ? date : null);
        auditTrail.oldValue = (oldObject == null ? null : getJsonValue(oldObject));
        auditTrail.newValue = (newObject == null ? null : getJsonValue(newObject));
        return auditTrail;
    }

    private String getJsonValue(final Object object) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    /*
    @Component
    static class AuditJpaUpdater {
        @PersistenceContext private EntityManager entityManager;
        @Transactional(propagation = Propagation.REQUIRES_NEW) //new transaction helps us to retrieve the old value still inside the db
        public <T> T findOldObject(Class<T> clazz, String id) {
            return entityManager.find(clazz, id);
        }
    */
    @Component
    static class AuditJpaUpdater {
        //todo: needs lazy loading disabled
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public <T> T findOldObject(Class<T> clazz, String id) {
            var repo = (CrudRepository) new Repositories(context).getRepositoryFor(clazz).get();
            return (T) repo.findById(id).get();
        }
    }

    /*
    @Component
    @RegisterReflectionForBinding(AuditTrailListener.AuditTrail.class)
    static class AuditJpaInserter {
        private final DataSource dataSource;
        private final String     schemaPrefix;

        public AuditJpaInserter(DataSource dataSource, @Value("${multi-tenancy.schema-prefix:_}") String schemaPrefix) {
            this.dataSource = dataSource;
            this.schemaPrefix = schemaPrefix;
        }

        public void insertAudit(AuditTrailListener.AuditTrail auditTrail, Object object) { //we cannot use jpa because of the dynamic table name
            new SimpleJdbcInsert(dataSource)
                    .withSchemaName(schemaPrefix + HttpInterceptor.getTenantId())
                    .withTableName("audit_trail")
                    .execute(new BeanPropertySqlParameterSource(auditTrail));
        }
    }

     */

    private static String getId(Object object) {
        return String.valueOf(context.getBean(EntityManagerFactory.class).getPersistenceUnitUtil().getIdentifier(object));
    }

    private static String getTableName(Object object) {
        return object.getClass().getSimpleName().replaceAll("Eo", "").toLowerCase();
    }

}



