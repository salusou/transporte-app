package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompaniesXUsersMapperTest {

    private CompaniesXUsersMapper companiesXUsersMapper;

    @BeforeEach
    public void setUp() {
        companiesXUsersMapper = new CompaniesXUsersMapperImpl();
    }
}
