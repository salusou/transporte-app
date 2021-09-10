package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CitiesMapperTest {

    private CitiesMapper citiesMapper;

    @BeforeEach
    public void setUp() {
        citiesMapper = new CitiesMapperImpl();
    }
}
