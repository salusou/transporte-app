package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParkingSectorSpaceMapperTest {

    private ParkingSectorSpaceMapper parkingSectorSpaceMapper;

    @BeforeEach
    public void setUp() {
        parkingSectorSpaceMapper = new ParkingSectorSpaceMapperImpl();
    }
}
