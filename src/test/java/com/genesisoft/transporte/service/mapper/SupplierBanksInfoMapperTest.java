package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplierBanksInfoMapperTest {

    private SupplierBanksInfoMapper supplierBanksInfoMapper;

    @BeforeEach
    public void setUp() {
        supplierBanksInfoMapper = new SupplierBanksInfoMapperImpl();
    }
}
