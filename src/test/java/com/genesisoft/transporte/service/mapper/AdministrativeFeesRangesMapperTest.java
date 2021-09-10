package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdministrativeFeesRangesMapperTest {

    private AdministrativeFeesRangesMapper administrativeFeesRangesMapper;

    @BeforeEach
    public void setUp() {
        administrativeFeesRangesMapper = new AdministrativeFeesRangesMapperImpl();
    }
}
