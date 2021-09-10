package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SuppliersMapperTest {

    private SuppliersMapper suppliersMapper;

    @BeforeEach
    public void setUp() {
        suppliersMapper = new SuppliersMapperImpl();
    }
}
