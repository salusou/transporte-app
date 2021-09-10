package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompaniesMapperTest {

    private CompaniesMapper companiesMapper;

    @BeforeEach
    public void setUp() {
        companiesMapper = new CompaniesMapperImpl();
    }
}
