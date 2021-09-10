package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleInspectionsMapperTest {

    private VehicleInspectionsMapper vehicleInspectionsMapper;

    @BeforeEach
    public void setUp() {
        vehicleInspectionsMapper = new VehicleInspectionsMapperImpl();
    }
}
