package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleControlsMapperTest {

    private VehicleControlsMapper vehicleControlsMapper;

    @BeforeEach
    public void setUp() {
        vehicleControlsMapper = new VehicleControlsMapperImpl();
    }
}
