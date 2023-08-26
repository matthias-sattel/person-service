package org.goafabric.personservice.repository.extensions;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.Arrays;

@Configuration
@ImportRuntimeHints(JaversNativeConfiguration.ApplicationRuntimeHints.class)
public class JaversNativeConfiguration {

    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

            hints.resources().registerResourceBundle("org.aspectj.weaver.weaver-messages");

            var lst = Arrays.asList(
                    "java.util.ArrayList",
                    "org.javers.spring.jpa.JpaHibernateConnectionProvider",
                    "org.polyjdbc.core.query.QueryRunnerFactory",

                    "org.javers.core.Javers",
                    "org.javers.core.JaversBuilder",
                    "org.javers.core.JaversBuilderPlugin",
                    "org.javers.core.JaversCore",
                    "org.javers.core.JaversCoreProperties",
                    "org.javers.core.JaversCoreProperties$PrettyPrintDateFormats",
                    "org.javers.core.commit.Commit",
                    "org.javers.core.commit.CommitFactory",
                    "org.javers.core.commit.CommitId",
                    "org.javers.core.commit.CommitIdFactory",
                    "org.javers.core.commit.CommitSeqGenerator",
                    "org.javers.core.commit.DistributedCommitSeqGenerator",
                    "org.javers.core.diff.DiffFactory",
                    "org.javers.core.diff.appenders.ArrayChangeAppender",
                    "org.javers.core.diff.appenders.CollectionAsListChangeAppender",
                    "org.javers.core.diff.appenders.MapChangeAppender",
                    "org.javers.core.diff.appenders.NewObjectAppender",
                    "org.javers.core.diff.appenders.ObjectRemovedAppender",
                    "org.javers.core.diff.appenders.OptionalChangeAppender",
                    "org.javers.core.diff.appenders.ReferenceChangeAppender",
                    "org.javers.core.diff.appenders.SetChangeAppender",
                    "org.javers.core.diff.appenders.SimpleListChangeAppender",
                    "org.javers.core.diff.appenders.ValueChangeAppender",
                    "org.javers.core.graph.CollectionsCdoFactory",
                    "org.javers.core.graph.LiveCdoFactory",
                    "org.javers.core.graph.LiveGraphFactory",
                    "org.javers.core.graph.ObjectAccessHook",
                    "org.javers.core.graph.ObjectHasher",
                    "org.javers.core.graph.TailoredJaversFieldFactory",
                    "org.javers.core.json.JsonConverter",
                    "org.javers.core.json.JsonConverterBuilder",
                    "org.javers.core.json.typeadapter.change.ArrayChangeTypeAdapter",
                    "org.javers.core.json.typeadapter.change.ChangeTypeAdapter",
                    "org.javers.core.json.typeadapter.change.ListChangeTypeAdapter",
                    "org.javers.core.json.typeadapter.change.MapChangeTypeAdapter",
                    "org.javers.core.json.typeadapter.change.NewObjectTypeAdapter",
                    "org.javers.core.json.typeadapter.change.ObjectRemovedTypeAdapter",
                    "org.javers.core.json.typeadapter.change.ReferenceChangeTypeAdapter",
                    "org.javers.core.json.typeadapter.change.SetChangeTypeAdapter",

                    "org.javers.core.json.typeadapter.change.ValueChangeTypeAdapter",

                    "org.javers.core.json.typeadapter.commit.CdoSnapshotStateTypeAdapter",
                    "org.javers.core.json.typeadapter.commit.CdoSnapshotTypeAdapter",

                    "org.javers.core.json.typeadapter.commit.CommitIdTypeAdapter",
                    "org.javers.core.json.typeadapter.commit.CommitMetadataTypeAdapter",
                    "org.javers.core.json.typeadapter.commit.GlobalIdTypeAdapter",

                    "org.javers.core.json.typeadapter.commit.JsonElementFakeAdapter",
                    "org.javers.core.metamodel.object.CdoSnapshot",
                    "org.javers.core.metamodel.object.GlobalId",
                    "org.javers.core.metamodel.object.GlobalIdFactory",

                    "org.javers.core.metamodel.scanner.AnnotationNamesProvider",
                    "org.javers.core.metamodel.scanner.ClassAnnotationsScanner",

                    "org.javers.core.metamodel.scanner.ClassScanner",

                    "org.javers.core.metamodel.scanner.FieldBasedPropertyScanner",

                    "org.javers.core.metamodel.type.EntityType",
                    "org.javers.core.metamodel.type.TypeMapper",

                    "org.javers.core.snapshot.ChangedCdoSnapshotsFactory",

                    "org.javers.core.snapshot.SnapshotDiffer",

                    "org.javers.core.snapshot.SnapshotFactory",

                    "org.javers.core.snapshot.SnapshotGraphFactory",

                    "org.javers.guava.MultimapChangeAppender",

                    "org.javers.guava.MultisetChangeAppender",
                    "org.javers.hibernate.integration.HibernateUnproxyObjectAccessHook",
                    "org.javers.repository.api.JaversExtendedRepository",

                    "org.javers.repository.api.JaversRepository",
                    "org.javers.repository.api.QueryParams",
                    "org.javers.repository.jql.ChangesQueryRunner",

                    "org.javers.repository.jql.QueryCompiler",

                    "org.javers.repository.jql.QueryRunner",

                    "org.javers.repository.jql.ShadowQueryRunner",

                    "org.javers.repository.jql.ShadowStreamQueryRunner",

                    "org.javers.repository.jql.SnapshotQueryRunner",

                    "org.javers.repository.sql.ConnectionProvider",
                    "org.javers.repository.sql.DialectName",
                    "org.javers.repository.sql.JaversSqlRepository",

                    "org.javers.repository.sql.SqlRepositoryBuilder",
                    "org.javers.repository.sql.SqlRepositoryConfiguration",
                    "org.javers.repository.sql.finders.CdoSnapshotFinder",

                    "org.javers.repository.sql.finders.CommitPropertyFinder",

                    "org.javers.repository.sql.repositories.CdoSnapshotRepository",

                    "org.javers.repository.sql.repositories.CommitMetadataRepository",

                    "org.javers.repository.sql.repositories.GlobalIdRepository",

                    "org.javers.repository.sql.schema.FixedSchemaFactory",

                    "org.javers.repository.sql.schema.JaversSchemaManager",

                    "org.javers.repository.sql.schema.SchemaNameAware",
                    "org.javers.repository.sql.schema.TableNameProvider",

                    "org.javers.repository.sql.session.Session",
                    "org.javers.shadow.ShadowFactory",

                    "org.javers.spring.JaversSpringProperties",
                    "org.javers.spring.RegisterJsonTypeAdaptersPlugin",

                    "org.javers.spring.annotation.JaversAuditable",
                    "org.javers.spring.annotation.JaversAuditableConditionalDelete",
                    "org.javers.spring.annotation.JaversAuditableDelete",
                    "org.javers.spring.annotation.JaversSpringDataAuditable",
                    "org.javers.spring.auditable.AuthorProvider",
                    "org.javers.spring.auditable.CommitPropertiesProvider",
                    "org.javers.spring.auditable.aspect.JaversAuditableAspect",

                    "org.javers.spring.auditable.aspect.JaversCommitAdvice",
                    "org.javers.spring.auditable.aspect.springdata.AbstractSpringAuditableRepositoryAspect",
                    "org.javers.spring.auditable.aspect.springdatajpa.JaversSpringDataJpaAuditableRepositoryAspect",

                    "org.javers.spring.boot.sql.JaversSqlAutoConfiguration",
                    "org.javers.spring.boot.sql.JaversSqlAutoConfiguration$$SpringCGLIB$$0",
                    "org.javers.spring.boot.sql.JaversSqlAutoConfiguration$$SpringCGLIB$$1",
                    "org.javers.spring.boot.sql.JaversSqlAutoConfiguration$$SpringCGLIB$$2",
                    "org.javers.spring.boot.sql.JaversSqlProperties",
                    "org.javers.spring.jpa.JaversTransactionalJpaDecorator",
                    "org.javers.spring.jpa.JaversTransactionalJpaDecorator$1",
                    "org.javers.spring.jpa.JpaHibernateConnectionProvider"
            );

            lst.stream().forEach(clazz -> {
                try {
                    hints.reflection().registerType(Class.forName(clazz), builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            });

            /*
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

             */

        }
    }

    // javers.newObjectSnapshot: "true"

}
