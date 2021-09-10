package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HousingVehicleItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HousingVehicleItem.class);
        HousingVehicleItem housingVehicleItem1 = new HousingVehicleItem();
        housingVehicleItem1.setId(1L);
        HousingVehicleItem housingVehicleItem2 = new HousingVehicleItem();
        housingVehicleItem2.setId(housingVehicleItem1.getId());
        assertThat(housingVehicleItem1).isEqualTo(housingVehicleItem2);
        housingVehicleItem2.setId(2L);
        assertThat(housingVehicleItem1).isNotEqualTo(housingVehicleItem2);
        housingVehicleItem1.setId(null);
        assertThat(housingVehicleItem1).isNotEqualTo(housingVehicleItem2);
    }
}
