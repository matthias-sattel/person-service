package org.goafabric.personservice.adapter;

import org.goafabric.personservice.logic.port.CalleeService;
import org.springframework.stereotype.Component;

@Component
public class CalleeServiceImpl implements CalleeService {

    private final CalleeServiceAdapter calleeServiceAdapter;

    public CalleeServiceImpl(CalleeServiceAdapter calleeServiceAdapter) {
        this.calleeServiceAdapter = calleeServiceAdapter;
    }
    @Override
    public String sayMyName(String name) {
        return calleeServiceAdapter.sayMyName(name).message();
    }
}
