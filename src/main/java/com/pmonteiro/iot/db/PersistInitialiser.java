package com.pmonteiro.iot.db;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class PersistInitialiser {

    @Inject
    public PersistInitialiser(PersistService service) {
        service.start();
    }
}
