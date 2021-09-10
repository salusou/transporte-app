package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleControlAttachmentsMapperTest {

    private VehicleControlAttachmentsMapper vehicleControlAttachmentsMapper;

    @BeforeEach
    public void setUp() {
        vehicleControlAttachmentsMapper = new VehicleControlAttachmentsMapperImpl();
    }
}
