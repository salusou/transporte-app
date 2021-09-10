package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HousingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HousingDTO.class);
        HousingDTO housingDTO1 = new HousingDTO();
        housingDTO1.setId(1L);
        HousingDTO housingDTO2 = new HousingDTO();
        assertThat(housingDTO1).isNotEqualTo(housingDTO2);
        housingDTO2.setId(housingDTO1.getId());
        assertThat(housingDTO1).isEqualTo(housingDTO2);
        housingDTO2.setId(2L);
        assertThat(housingDTO1).isNotEqualTo(housingDTO2);
        housingDTO1.setId(null);
        assertThat(housingDTO1).isNotEqualTo(housingDTO2);
    }
}
