package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlsDTO.class);
        VehicleControlsDTO vehicleControlsDTO1 = new VehicleControlsDTO();
        vehicleControlsDTO1.setId(1L);
        VehicleControlsDTO vehicleControlsDTO2 = new VehicleControlsDTO();
        assertThat(vehicleControlsDTO1).isNotEqualTo(vehicleControlsDTO2);
        vehicleControlsDTO2.setId(vehicleControlsDTO1.getId());
        assertThat(vehicleControlsDTO1).isEqualTo(vehicleControlsDTO2);
        vehicleControlsDTO2.setId(2L);
        assertThat(vehicleControlsDTO1).isNotEqualTo(vehicleControlsDTO2);
        vehicleControlsDTO1.setId(null);
        assertThat(vehicleControlsDTO1).isNotEqualTo(vehicleControlsDTO2);
    }
}
