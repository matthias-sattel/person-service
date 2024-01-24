package org.goafabric.personservice;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("org.goafabric.personservice");

    @Test
    public void testLayerDependencies() {
        layeredArchitecture().consideringOnlyDependenciesInLayers()
                .layer("Controller").definedBy("org.goafabric.personservice.controller")
                .layer("Logic").definedBy("org.goafabric.personservice.logic")
                .layer("Persistence").definedBy("org.goafabric.personservice.repository.*")
                .layer("Adapter").definedBy("org.goafabric.personservice.adapter")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Logic").mayNotAccessAnyLayer()
                //.whereLayer("Persistence").mayOnlyBeAccessedByLayers("Persistence")
                //.whereLayer("Adapter").mayNotAccessAnyLayer()
                .check(classes);
    }

}
