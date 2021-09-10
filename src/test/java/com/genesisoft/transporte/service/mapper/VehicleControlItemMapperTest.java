package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleControlItemMapperTest {

    private VehicleControlItemMapper vehicleControlItemMapper;

    @BeforeEach
    public void setUp() {
        vehicleControlItemMapper = new VehicleControlItemMapperImpl();
    }
}
