package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleInspectionsImagensDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleInspectionsImagensDTO.class);
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO1 = new VehicleInspectionsImagensDTO();
        vehicleInspectionsImagensDTO1.setId(1L);
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO2 = new VehicleInspectionsImagensDTO();
        assertThat(vehicleInspectionsImagensDTO1).isNotEqualTo(vehicleInspectionsImagensDTO2);
        vehicleInspectionsImagensDTO2.setId(vehicleInspectionsImagensDTO1.getId());
        assertThat(vehicleInspectionsImagensDTO1).isEqualTo(vehicleInspectionsImagensDTO2);
        vehicleInspectionsImagensDTO2.setId(2L);
        assertThat(vehicleInspectionsImagensDTO1).isNotEqualTo(vehicleInspectionsImagensDTO2);
        vehicleInspectionsImagensDTO1.setId(null);
        assertThat(vehicleInspectionsImagensDTO1).isNotEqualTo(vehicleInspectionsImagensDTO2);
    }
}
