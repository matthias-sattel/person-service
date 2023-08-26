package org.goafabric.personservice.extensions;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GraalVmIT {

    @Test
    public void test() throws IOException {
        var lst = Files.readAllLines(new File("/Users/andreas/Downloads/javers/reflect-config.json").toPath());
        lst.stream().forEach(line -> {
            if (line.contains("org.javers")) {
                System.out.println(line
                        .split("name")[1]
                        .split(",")[0]
                        .replaceAll("\":", "")
                        .replaceAll("\"<init>\"", "")
                        + (","));
            }
        });
    }

    /*
    java -Dspring.aot.enabled=true -agentlib:native-image-agent=config-output-dir=/Users/andreas/Downloads \
    -jar build/libs/callee-service-3.1.2-console-SNAPSHOT.jar
     */

    //grep "org.javers.core" reflect-config.json | grep -v "<init>" | sed -n 's/.*"name":"\([^"]*\)",.*/\1/p'

}
