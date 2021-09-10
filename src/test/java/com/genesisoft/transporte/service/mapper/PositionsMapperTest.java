package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionsMapperTest {

    private PositionsMapper positionsMapper;

    @BeforeEach
    public void setUp() {
        positionsMapper = new PositionsMapperImpl();
    }
}
