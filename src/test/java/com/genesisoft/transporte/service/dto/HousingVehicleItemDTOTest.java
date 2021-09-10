package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HousingVehicleItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HousingVehicleItemDTO.class);
        HousingVehicleItemDTO housingVehicleItemDTO1 = new HousingVehicleItemDTO();
        housingVehicleItemDTO1.setId(1L);
        HousingVehicleItemDTO housingVehicleItemDTO2 = new HousingVehicleItemDTO();
        assertThat(housingVehicleItemDTO1).isNotEqualTo(housingVehicleItemDTO2);
        housingVehicleItemDTO2.setId(housingVehicleItemDTO1.getId());
        assertThat(housingVehicleItemDTO1).isEqualTo(housingVehicleItemDTO2);
        housingVehicleItemDTO2.setId(2L);
        assertThat(housingVehicleItemDTO1).isNotEqualTo(housingVehicleItemDTO2);
        housingVehicleItemDTO1.setId(null);
        assertThat(housingVehicleItemDTO1).isNotEqualTo(housingVehicleItemDTO2);
    }
}
