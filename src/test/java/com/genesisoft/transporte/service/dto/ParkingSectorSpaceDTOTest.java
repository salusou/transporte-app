package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkingSectorSpaceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingSectorSpaceDTO.class);
        ParkingSectorSpaceDTO parkingSectorSpaceDTO1 = new ParkingSectorSpaceDTO();
        parkingSectorSpaceDTO1.setId(1L);
        ParkingSectorSpaceDTO parkingSectorSpaceDTO2 = new ParkingSectorSpaceDTO();
        assertThat(parkingSectorSpaceDTO1).isNotEqualTo(parkingSectorSpaceDTO2);
        parkingSectorSpaceDTO2.setId(parkingSectorSpaceDTO1.getId());
        assertThat(parkingSectorSpaceDTO1).isEqualTo(parkingSectorSpaceDTO2);
        parkingSectorSpaceDTO2.setId(2L);
        assertThat(parkingSectorSpaceDTO1).isNotEqualTo(parkingSectorSpaceDTO2);
        parkingSectorSpaceDTO1.setId(null);
        assertThat(parkingSectorSpaceDTO1).isNotEqualTo(parkingSectorSpaceDTO2);
    }
}
