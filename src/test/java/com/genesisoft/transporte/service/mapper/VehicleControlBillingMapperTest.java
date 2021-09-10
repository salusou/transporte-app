package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleControlBillingMapperTest {

    private VehicleControlBillingMapper vehicleControlBillingMapper;

    @BeforeEach
    public void setUp() {
        vehicleControlBillingMapper = new VehicleControlBillingMapperImpl();
    }
}
