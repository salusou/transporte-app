package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AffiliatesMapperTest {

    private AffiliatesMapper affiliatesMapper;

    @BeforeEach
    public void setUp() {
        affiliatesMapper = new AffiliatesMapperImpl();
    }
}
