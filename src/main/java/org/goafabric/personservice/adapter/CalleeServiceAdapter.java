package org.goafabric.personservice.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CalleeServiceAdapter {
    @Autowired
    private CalleeServiceClient calleeServiceClient;

    public Boolean isAlive() {
        log.info("Calling CalleService ...");
        final Boolean isAlive = calleeServiceClient.isAlive();
        log.info("got: " + isAlive);
        return isAlive;
    }
}
