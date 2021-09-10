package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsurancesMapperTest {

    private InsurancesMapper insurancesMapper;

    @BeforeEach
    public void setUp() {
        insurancesMapper = new InsurancesMapperImpl();
    }
}
