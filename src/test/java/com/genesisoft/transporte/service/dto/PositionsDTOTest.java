package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PositionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PositionsDTO.class);
        PositionsDTO positionsDTO1 = new PositionsDTO();
        positionsDTO1.setId(1L);
        PositionsDTO positionsDTO2 = new PositionsDTO();
        assertThat(positionsDTO1).isNotEqualTo(positionsDTO2);
        positionsDTO2.setId(positionsDTO1.getId());
        assertThat(positionsDTO1).isEqualTo(positionsDTO2);
        positionsDTO2.setId(2L);
        assertThat(positionsDTO1).isNotEqualTo(positionsDTO2);
        positionsDTO1.setId(null);
        assertThat(positionsDTO1).isNotEqualTo(positionsDTO2);
    }
}
