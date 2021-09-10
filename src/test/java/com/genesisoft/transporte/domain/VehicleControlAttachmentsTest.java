package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlAttachmentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlAttachments.class);
        VehicleControlAttachments vehicleControlAttachments1 = new VehicleControlAttachments();
        vehicleControlAttachments1.setId(1L);
        VehicleControlAttachments vehicleControlAttachments2 = new VehicleControlAttachments();
        vehicleControlAttachments2.setId(vehicleControlAttachments1.getId());
        assertThat(vehicleControlAttachments1).isEqualTo(vehicleControlAttachments2);
        vehicleControlAttachments2.setId(2L);
        assertThat(vehicleControlAttachments1).isNotEqualTo(vehicleControlAttachments2);
        vehicleControlAttachments1.setId(null);
        assertThat(vehicleControlAttachments1).isNotEqualTo(vehicleControlAttachments2);
    }
}
