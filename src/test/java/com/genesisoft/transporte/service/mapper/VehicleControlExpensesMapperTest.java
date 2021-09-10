package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleControlExpensesMapperTest {

    private VehicleControlExpensesMapper vehicleControlExpensesMapper;

    @BeforeEach
    public void setUp() {
        vehicleControlExpensesMapper = new VehicleControlExpensesMapperImpl();
    }
}
