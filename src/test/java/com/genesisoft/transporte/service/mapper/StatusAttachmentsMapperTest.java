package com.genesisoft.transporte.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatusAttachmentsMapperTest {

    private StatusAttachmentsMapper statusAttachmentsMapper;

    @BeforeEach
    public void setUp() {
        statusAttachmentsMapper = new StatusAttachmentsMapperImpl();
    }
}
