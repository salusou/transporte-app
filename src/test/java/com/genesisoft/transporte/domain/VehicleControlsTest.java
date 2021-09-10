package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControls.class);
        VehicleControls vehicleControls1 = new VehicleControls();
        vehicleControls1.setId(1L);
        VehicleControls vehicleControls2 = new VehicleControls();
        vehicleControls2.setId(vehicleControls1.getId());
        assertThat(vehicleControls1).isEqualTo(vehicleControls2);
        vehicleControls2.setId(2L);
        assertThat(vehicleControls1).isNotEqualTo(vehicleControls2);
        vehicleControls1.setId(null);
        assertThat(vehicleControls1).isNotEqualTo(vehicleControls2);
    }
}
