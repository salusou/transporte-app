package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleInspectionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleInspections.class);
        VehicleInspections vehicleInspections1 = new VehicleInspections();
        vehicleInspections1.setId(1L);
        VehicleInspections vehicleInspections2 = new VehicleInspections();
        vehicleInspections2.setId(vehicleInspections1.getId());
        assertThat(vehicleInspections1).isEqualTo(vehicleInspections2);
        vehicleInspections2.setId(2L);
        assertThat(vehicleInspections1).isNotEqualTo(vehicleInspections2);
        vehicleInspections1.setId(null);
        assertThat(vehicleInspections1).isNotEqualTo(vehicleInspections2);
    }
}
