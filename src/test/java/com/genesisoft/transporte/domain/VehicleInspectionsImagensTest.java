package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleInspectionsImagensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleInspectionsImagens.class);
        VehicleInspectionsImagens vehicleInspectionsImagens1 = new VehicleInspectionsImagens();
        vehicleInspectionsImagens1.setId(1L);
        VehicleInspectionsImagens vehicleInspectionsImagens2 = new VehicleInspectionsImagens();
        vehicleInspectionsImagens2.setId(vehicleInspectionsImagens1.getId());
        assertThat(vehicleInspectionsImagens1).isEqualTo(vehicleInspectionsImagens2);
        vehicleInspectionsImagens2.setId(2L);
        assertThat(vehicleInspectionsImagens1).isNotEqualTo(vehicleInspectionsImagens2);
        vehicleInspectionsImagens1.setId(null);
        assertThat(vehicleInspectionsImagens1).isNotEqualTo(vehicleInspectionsImagens2);
    }
}
