package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlBillingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlBillingDTO.class);
        VehicleControlBillingDTO vehicleControlBillingDTO1 = new VehicleControlBillingDTO();
        vehicleControlBillingDTO1.setId(1L);
        VehicleControlBillingDTO vehicleControlBillingDTO2 = new VehicleControlBillingDTO();
        assertThat(vehicleControlBillingDTO1).isNotEqualTo(vehicleControlBillingDTO2);
        vehicleControlBillingDTO2.setId(vehicleControlBillingDTO1.getId());
        assertThat(vehicleControlBillingDTO1).isEqualTo(vehicleControlBillingDTO2);
        vehicleControlBillingDTO2.setId(2L);
        assertThat(vehicleControlBillingDTO1).isNotEqualTo(vehicleControlBillingDTO2);
        vehicleControlBillingDTO1.setId(null);
        assertThat(vehicleControlBillingDTO1).isNotEqualTo(vehicleControlBillingDTO2);
    }
}
