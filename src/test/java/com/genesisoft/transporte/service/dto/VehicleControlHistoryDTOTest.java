package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlHistoryDTO.class);
        VehicleControlHistoryDTO vehicleControlHistoryDTO1 = new VehicleControlHistoryDTO();
        vehicleControlHistoryDTO1.setId(1L);
        VehicleControlHistoryDTO vehicleControlHistoryDTO2 = new VehicleControlHistoryDTO();
        assertThat(vehicleControlHistoryDTO1).isNotEqualTo(vehicleControlHistoryDTO2);
        vehicleControlHistoryDTO2.setId(vehicleControlHistoryDTO1.getId());
        assertThat(vehicleControlHistoryDTO1).isEqualTo(vehicleControlHistoryDTO2);
        vehicleControlHistoryDTO2.setId(2L);
        assertThat(vehicleControlHistoryDTO1).isNotEqualTo(vehicleControlHistoryDTO2);
        vehicleControlHistoryDTO1.setId(null);
        assertThat(vehicleControlHistoryDTO1).isNotEqualTo(vehicleControlHistoryDTO2);
    }
}
