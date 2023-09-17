package org.goafabric.personservice.repository.extensions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.goafabric.personservice.repository.entity.AuditAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Aspect
@Component
public class AuditrepositoryAspect {
    @AfterReturning("execution(public * delete(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onDeleteExecuted(JoinPoint pjp) {
        onDelete(pjp);
    }

    @AfterReturning("execution(public * deleteById(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onDeleteByIdExecuted(JoinPoint pjp) {
        onDelete(pjp);
    }

    @AfterReturning("execution(public * deleteAll(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onDeleteAllExecuted(JoinPoint pjp) {
        onDelete(pjp);
    }

    @AfterReturning(value = "execution(public * save(..)) && this(org.springframework.data.repository.CrudRepository)", returning = "responseEntity")
    public void onSaveExecuted(JoinPoint pjp, Object responseEntity) {
        onSave(pjp, responseEntity);
    }

    @AfterReturning(value = "execution(public * saveAll(..)) && this(org.springframework.data.repository.CrudRepository)", returning = "responseEntity")
    public void onSaveAllExecuted(JoinPoint pjp, Object responseEntity) {
        onSave(pjp, responseEntity);
    }


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final HashMap<String, Integer> interactions = new HashMap<>();

    @Autowired
    private AuditJpaUpdater updater;

    protected void onSave(JoinPoint pjp, Object returnedObject) {
        getRepositoryInterface(pjp).ifPresent(i -> {
                    //RepositoryMetadata metadata = DefaultRepositoryMetadata.getMetadata(i);
                    collectReturnedObjects(returnedObject)
                            .forEach(object -> auditLog(object, "create"));
        });
    }

    protected void onDelete(JoinPoint pjp) {
        getRepositoryInterface(pjp).ifPresent( i -> {
            //RepositoryMetadata metadata = DefaultRepositoryMetadata.getMetadata(i);
            for (Object deletedObject : collectArguments(pjp)) {
                auditLog(deletedObject, "delete");
                //handleDelete(metadata, deletedObject);
            }
        });
    }

    private void auditLog(Object object, String type) {
        try {
            interactions.put(type, interactions.get(type) == null ? 1 : interactions.get(type) +1 );
            var id = getId(object);

            /*
            Optional<?> x = updater.findOldObject(object.getClass(), id);
            if (x.isPresent()) {
                type = "update";
                object = x.get();
            }

             */

            log.info("new audit of type {} with id {}", type, id);
            if (type.equals("create")) {
                log.info(getJsonValue(object));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private static String getId(Object object) {
        return object instanceof AuditAware ? ((AuditAware) object).getId() : object.toString();
    }

    private String getJsonValue(final Object object) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    private Optional<Class> getRepositoryInterface(JoinPoint pjp) {
        for (Class i : pjp.getTarget().getClass().getInterfaces()) {
            //if (i.isAnnotationPresent(JaversSpringDataAuditable.class) && CrudRepository.class.isAssignableFrom(i)) {
            if (CrudRepository.class.isAssignableFrom(i)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private static Iterable<Object> collectReturnedObjects(Object returnedObject){
        return returnedObject instanceof Collection ? (Iterable) returnedObject : Collections.singletonList(returnedObject);
    }

    private static List<Object> collectArguments(JoinPoint jp){
        List<Object> result = new ArrayList<>();

        for (Object arg: jp.getArgs()) {
            if (arg instanceof Collection) {
                result.addAll((Collection)arg);
            } else {
                result.add(arg);
            }
        }
        return result;
    }

    @Component
    static class AuditJpaUpdater {
        @Autowired
        private ApplicationContext context;
        //todo: needs lazy loading disabled@Transactional(propagation = Propagation.REQUIRES_NEW) //new transaction helps us to retrieve the old value still inside the db
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public <T> Optional<T> findOldObject(Class<T> clazz, String id) {
            var repo = (CrudRepository) new Repositories(context).getRepositoryFor(clazz).get();
            return (Optional<T>) repo.findById(id);
        }
    }

    //*******************

    /*

   void handleDelete(RepositoryMetadata repositoryMetadata, Object domainObjectOrId) {
            if (isIdClass(repositoryMetadata, domainObjectOrId)) {
                Class<?> domainType = repositoryMetadata.getDomainType();
                System.out.println(domainType);
            } else if (isDomainClass(repositoryMetadata, domainObjectOrId)) {
                System.out.println(domainObjectOrId);
            } else {
                throw new IllegalArgumentException("Domain object or object id expected");
            }
    }

    private boolean isDomainClass(RepositoryMetadata metadata, Object o) {
        return metadata.getDomainType().isAssignableFrom(o.getClass());
    }

    private boolean isIdClass(RepositoryMetadata metadata, Object o) {
        return metadata.getIdType().isAssignableFrom(o.getClass());
    }

    private static Iterable<Object> collectReturnedObjects(Object returnedObject){
        if (returnedObject instanceof Iterable) {
            return (Iterable)returnedObject;
        }
        if (returnedObject == null) {
            return Collections.emptyList();
        }
        return returnedObject != null ? List.of(returnedObject) : Collections.emptyList();
    }

     */


}
