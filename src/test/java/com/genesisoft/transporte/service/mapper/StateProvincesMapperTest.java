package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateProvincesMapperTest {

    private StateProvincesMapper stateProvincesMapper;

    @BeforeEach
    public void setUp() {
        stateProvincesMapper = new StateProvincesMapperImpl();
    }
}
