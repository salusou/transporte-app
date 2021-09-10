package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParkingSectorMapperTest {

    private ParkingSectorMapper parkingSectorMapper;

    @BeforeEach
    public void setUp() {
        parkingSectorMapper = new ParkingSectorMapperImpl();
    }
}
