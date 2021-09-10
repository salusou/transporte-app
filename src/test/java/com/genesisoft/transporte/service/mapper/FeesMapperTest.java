package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeesMapperTest {

    private FeesMapper feesMapper;

    @BeforeEach
    public void setUp() {
        feesMapper = new FeesMapperImpl();
    }
}
