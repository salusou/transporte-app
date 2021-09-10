package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceProvidedMapperTest {

    private ServiceProvidedMapper serviceProvidedMapper;

    @BeforeEach
    public void setUp() {
        serviceProvidedMapper = new ServiceProvidedMapperImpl();
    }
}
