package org.goafabric.personservice.repository.extensions;

import org.javers.repository.sql.JaversSqlRepository;
import org.javers.repository.sql.finders.CdoSnapshotFinder;
import org.javers.repository.sql.finders.CommitPropertyFinder;
import org.javers.repository.sql.repositories.CdoSnapshotRepository;
import org.javers.repository.sql.repositories.CommitMetadataRepository;
import org.javers.repository.sql.repositories.GlobalIdRepository;
import org.javers.repository.sql.schema.FixedSchemaFactory;
import org.javers.repository.sql.schema.JaversSchemaManager;
import org.javers.repository.sql.schema.TableNameProvider;
import org.javers.spring.annotation.*;
import org.javers.spring.auditable.aspect.JaversAuditableAspect;
import org.javers.spring.auditable.aspect.springdata.JaversSpringDataAuditableRepositoryAspect;
import org.javers.spring.auditable.aspect.springdatajpa.JaversSpringDataJpaAuditableRepositoryAspect;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(JaversNativeConfiguration.ApplicationRuntimeHints.class)
public class JaversNativeConfiguration {

    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

            hints.resources().registerResourceBundle("org.aspectj.weaver.weaver-messages");

            hints.reflection().registerType(JaversAuditable.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(JaversAuditableAsync.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(JaversAuditableConditionalDelete.class,builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(JaversAuditableDelete.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(JaversSpringDataAuditable.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));

            hints.reflection().registerType(JaversSpringDataAuditableRepositoryAspect.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(JaversAuditableAspect.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(JaversSpringDataJpaAuditableRepositoryAspect.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));

            hints.reflection().registerType(JaversSqlRepository.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(CommitMetadataRepository.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(GlobalIdRepository.class,builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(CdoSnapshotRepository.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));

            hints.reflection().registerType(TableNameProvider.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(CdoSnapshotFinder.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(CommitPropertyFinder.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(JaversSchemaManager.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(FixedSchemaFactory.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));

            hints.reflection().registerType(org.javers.hibernate.integration.HibernateUnproxyObjectAccessHook.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(org.javers.core.json.JsonConverterBuilder.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(org.javers.core.metamodel.type.TypeMapper.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            hints.reflection().registerType(org.javers.core.metamodel.scanner.ClassScanner.class, builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));

            try {
                hints.reflection().registerType(Class.forName("org.javers.core.metamodel.scanner.FieldBasedPropertyScanner"), builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            } catch (ClassNotFoundException e) {throw new RuntimeException(e);}

            try {
                hints.reflection().registerType(Class.forName("org.javers.core.metamodel.scanner.AnnotationNamesProvider"), builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            } catch (ClassNotFoundException e) {throw new RuntimeException(e);}
            try {
                hints.reflection().registerType(Class.forName("org.javers.core.metamodel.scanner.ClassAnnotationsScanner"), builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
            } catch (ClassNotFoundException e) {throw new RuntimeException(e);}

        }
    }

    // javers.newObjectSnapshot: "true"

}
