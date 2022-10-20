package org.goafabric.personservice.adapter;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface CalleeServiceAdapterDec {

    @GetExchange("/callees/sayMyName")
    Callee sayMyName (@RequestParam("name") String name);

}
