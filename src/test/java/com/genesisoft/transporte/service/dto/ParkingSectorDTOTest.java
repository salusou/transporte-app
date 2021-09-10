package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkingSectorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingSectorDTO.class);
        ParkingSectorDTO parkingSectorDTO1 = new ParkingSectorDTO();
        parkingSectorDTO1.setId(1L);
        ParkingSectorDTO parkingSectorDTO2 = new ParkingSectorDTO();
        assertThat(parkingSectorDTO1).isNotEqualTo(parkingSectorDTO2);
        parkingSectorDTO2.setId(parkingSectorDTO1.getId());
        assertThat(parkingSectorDTO1).isEqualTo(parkingSectorDTO2);
        parkingSectorDTO2.setId(2L);
        assertThat(parkingSectorDTO1).isNotEqualTo(parkingSectorDTO2);
        parkingSectorDTO1.setId(null);
        assertThat(parkingSectorDTO1).isNotEqualTo(parkingSectorDTO2);
    }
}
