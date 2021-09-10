package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleControlHistoryMapperTest {

    private VehicleControlHistoryMapper vehicleControlHistoryMapper;

    @BeforeEach
    public void setUp() {
        vehicleControlHistoryMapper = new VehicleControlHistoryMapperImpl();
    }
}
