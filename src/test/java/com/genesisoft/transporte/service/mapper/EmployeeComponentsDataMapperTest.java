package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeComponentsDataMapperTest {

    private EmployeeComponentsDataMapper employeeComponentsDataMapper;

    @BeforeEach
    public void setUp() {
        employeeComponentsDataMapper = new EmployeeComponentsDataMapperImpl();
    }
}
