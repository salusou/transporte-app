package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CostCenterMapperTest {

    private CostCenterMapper costCenterMapper;

    @BeforeEach
    public void setUp() {
        costCenterMapper = new CostCenterMapperImpl();
    }
}
