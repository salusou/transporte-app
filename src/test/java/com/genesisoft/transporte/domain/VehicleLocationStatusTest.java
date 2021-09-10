package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleLocationStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleLocationStatus.class);
        VehicleLocationStatus vehicleLocationStatus1 = new VehicleLocationStatus();
        vehicleLocationStatus1.setId(1L);
        VehicleLocationStatus vehicleLocationStatus2 = new VehicleLocationStatus();
        vehicleLocationStatus2.setId(vehicleLocationStatus1.getId());
        assertThat(vehicleLocationStatus1).isEqualTo(vehicleLocationStatus2);
        vehicleLocationStatus2.setId(2L);
        assertThat(vehicleLocationStatus1).isNotEqualTo(vehicleLocationStatus2);
        vehicleLocationStatus1.setId(null);
        assertThat(vehicleLocationStatus1).isNotEqualTo(vehicleLocationStatus2);
    }
}
