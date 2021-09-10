package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplierContactsMapperTest {

    private SupplierContactsMapper supplierContactsMapper;

    @BeforeEach
    public void setUp() {
        supplierContactsMapper = new SupplierContactsMapperImpl();
    }
}
