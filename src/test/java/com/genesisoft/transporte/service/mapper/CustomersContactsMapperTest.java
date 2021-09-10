package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomersContactsMapperTest {

    private CustomersContactsMapper customersContactsMapper;

    @BeforeEach
    public void setUp() {
        customersContactsMapper = new CustomersContactsMapperImpl();
    }
}
