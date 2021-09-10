package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingDTO.class);
        ParkingDTO parkingDTO1 = new ParkingDTO();
        parkingDTO1.setId(1L);
        ParkingDTO parkingDTO2 = new ParkingDTO();
        assertThat(parkingDTO1).isNotEqualTo(parkingDTO2);
        parkingDTO2.setId(parkingDTO1.getId());
        assertThat(parkingDTO1).isEqualTo(parkingDTO2);
        parkingDTO2.setId(2L);
        assertThat(parkingDTO1).isNotEqualTo(parkingDTO2);
        parkingDTO1.setId(null);
        assertThat(parkingDTO1).isNotEqualTo(parkingDTO2);
    }
}
