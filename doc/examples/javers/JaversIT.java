package org.goafabric.personservice.extensions;

import org.goafabric.personservice.repository.entity.PersonEo;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JaversIT {
    @Autowired
    private Javers javers;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Test
    public void test() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(PersonEo.class);
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
        snapshots.forEach(snapshot -> log.info(javers.getJsonConverter().toJson(snapshot)));
    }
}
