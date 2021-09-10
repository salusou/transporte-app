package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlItemDTO.class);
        VehicleControlItemDTO vehicleControlItemDTO1 = new VehicleControlItemDTO();
        vehicleControlItemDTO1.setId(1L);
        VehicleControlItemDTO vehicleControlItemDTO2 = new VehicleControlItemDTO();
        assertThat(vehicleControlItemDTO1).isNotEqualTo(vehicleControlItemDTO2);
        vehicleControlItemDTO2.setId(vehicleControlItemDTO1.getId());
        assertThat(vehicleControlItemDTO1).isEqualTo(vehicleControlItemDTO2);
        vehicleControlItemDTO2.setId(2L);
        assertThat(vehicleControlItemDTO1).isNotEqualTo(vehicleControlItemDTO2);
        vehicleControlItemDTO1.setId(null);
        assertThat(vehicleControlItemDTO1).isNotEqualTo(vehicleControlItemDTO2);
    }
}
