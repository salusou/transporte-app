package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlHistory.class);
        VehicleControlHistory vehicleControlHistory1 = new VehicleControlHistory();
        vehicleControlHistory1.setId(1L);
        VehicleControlHistory vehicleControlHistory2 = new VehicleControlHistory();
        vehicleControlHistory2.setId(vehicleControlHistory1.getId());
        assertThat(vehicleControlHistory1).isEqualTo(vehicleControlHistory2);
        vehicleControlHistory2.setId(2L);
        assertThat(vehicleControlHistory1).isNotEqualTo(vehicleControlHistory2);
        vehicleControlHistory1.setId(null);
        assertThat(vehicleControlHistory1).isNotEqualTo(vehicleControlHistory2);
    }
}
