package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerAttachmentsMapperTest {

    private CustomerAttachmentsMapper customerAttachmentsMapper;

    @BeforeEach
    public void setUp() {
        customerAttachmentsMapper = new CustomerAttachmentsMapperImpl();
    }
}
