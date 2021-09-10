package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlItem.class);
        VehicleControlItem vehicleControlItem1 = new VehicleControlItem();
        vehicleControlItem1.setId(1L);
        VehicleControlItem vehicleControlItem2 = new VehicleControlItem();
        vehicleControlItem2.setId(vehicleControlItem1.getId());
        assertThat(vehicleControlItem1).isEqualTo(vehicleControlItem2);
        vehicleControlItem2.setId(2L);
        assertThat(vehicleControlItem1).isNotEqualTo(vehicleControlItem2);
        vehicleControlItem1.setId(null);
        assertThat(vehicleControlItem1).isNotEqualTo(vehicleControlItem2);
    }
}
