package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleLocationStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleLocationStatusDTO.class);
        VehicleLocationStatusDTO vehicleLocationStatusDTO1 = new VehicleLocationStatusDTO();
        vehicleLocationStatusDTO1.setId(1L);
        VehicleLocationStatusDTO vehicleLocationStatusDTO2 = new VehicleLocationStatusDTO();
        assertThat(vehicleLocationStatusDTO1).isNotEqualTo(vehicleLocationStatusDTO2);
        vehicleLocationStatusDTO2.setId(vehicleLocationStatusDTO1.getId());
        assertThat(vehicleLocationStatusDTO1).isEqualTo(vehicleLocationStatusDTO2);
        vehicleLocationStatusDTO2.setId(2L);
        assertThat(vehicleLocationStatusDTO1).isNotEqualTo(vehicleLocationStatusDTO2);
        vehicleLocationStatusDTO1.setId(null);
        assertThat(vehicleLocationStatusDTO1).isNotEqualTo(vehicleLocationStatusDTO2);
    }
}
