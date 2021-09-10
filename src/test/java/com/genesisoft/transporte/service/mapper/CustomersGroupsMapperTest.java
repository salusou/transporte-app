package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomersGroupsMapperTest {

    private CustomersGroupsMapper customersGroupsMapper;

    @BeforeEach
    public void setUp() {
        customersGroupsMapper = new CustomersGroupsMapperImpl();
    }
}
