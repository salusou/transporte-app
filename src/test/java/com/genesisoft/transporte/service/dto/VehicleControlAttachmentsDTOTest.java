package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlAttachmentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlAttachmentsDTO.class);
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO1 = new VehicleControlAttachmentsDTO();
        vehicleControlAttachmentsDTO1.setId(1L);
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO2 = new VehicleControlAttachmentsDTO();
        assertThat(vehicleControlAttachmentsDTO1).isNotEqualTo(vehicleControlAttachmentsDTO2);
        vehicleControlAttachmentsDTO2.setId(vehicleControlAttachmentsDTO1.getId());
        assertThat(vehicleControlAttachmentsDTO1).isEqualTo(vehicleControlAttachmentsDTO2);
        vehicleControlAttachmentsDTO2.setId(2L);
        assertThat(vehicleControlAttachmentsDTO1).isNotEqualTo(vehicleControlAttachmentsDTO2);
        vehicleControlAttachmentsDTO1.setId(null);
        assertThat(vehicleControlAttachmentsDTO1).isNotEqualTo(vehicleControlAttachmentsDTO2);
    }
}
