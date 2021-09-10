package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StateProvincesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvincesDTO.class);
        StateProvincesDTO stateProvincesDTO1 = new StateProvincesDTO();
        stateProvincesDTO1.setId(1L);
        StateProvincesDTO stateProvincesDTO2 = new StateProvincesDTO();
        assertThat(stateProvincesDTO1).isNotEqualTo(stateProvincesDTO2);
        stateProvincesDTO2.setId(stateProvincesDTO1.getId());
        assertThat(stateProvincesDTO1).isEqualTo(stateProvincesDTO2);
        stateProvincesDTO2.setId(2L);
        assertThat(stateProvincesDTO1).isNotEqualTo(stateProvincesDTO2);
        stateProvincesDTO1.setId(null);
        assertThat(stateProvincesDTO1).isNotEqualTo(stateProvincesDTO2);
    }
}
