package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleLocationStatusMapperTest {

    private VehicleLocationStatusMapper vehicleLocationStatusMapper;

    @BeforeEach
    public void setUp() {
        vehicleLocationStatusMapper = new VehicleLocationStatusMapperImpl();
    }
}
