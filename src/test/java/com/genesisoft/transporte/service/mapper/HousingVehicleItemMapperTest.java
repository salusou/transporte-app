package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HousingVehicleItemMapperTest {

    private HousingVehicleItemMapper housingVehicleItemMapper;

    @BeforeEach
    public void setUp() {
        housingVehicleItemMapper = new HousingVehicleItemMapperImpl();
    }
}
