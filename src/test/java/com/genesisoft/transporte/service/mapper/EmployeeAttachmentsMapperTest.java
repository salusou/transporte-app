package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeAttachmentsMapperTest {

    private EmployeeAttachmentsMapper employeeAttachmentsMapper;

    @BeforeEach
    public void setUp() {
        employeeAttachmentsMapper = new EmployeeAttachmentsMapperImpl();
    }
}
