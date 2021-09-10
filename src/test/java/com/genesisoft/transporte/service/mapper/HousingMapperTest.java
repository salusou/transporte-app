package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HousingMapperTest {

    private HousingMapper housingMapper;

    @BeforeEach
    public void setUp() {
        housingMapper = new HousingMapperImpl();
    }
}
