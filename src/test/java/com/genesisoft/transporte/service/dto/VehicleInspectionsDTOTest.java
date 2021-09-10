package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleInspectionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleInspectionsDTO.class);
        VehicleInspectionsDTO vehicleInspectionsDTO1 = new VehicleInspectionsDTO();
        vehicleInspectionsDTO1.setId(1L);
        VehicleInspectionsDTO vehicleInspectionsDTO2 = new VehicleInspectionsDTO();
        assertThat(vehicleInspectionsDTO1).isNotEqualTo(vehicleInspectionsDTO2);
        vehicleInspectionsDTO2.setId(vehicleInspectionsDTO1.getId());
        assertThat(vehicleInspectionsDTO1).isEqualTo(vehicleInspectionsDTO2);
        vehicleInspectionsDTO2.setId(2L);
        assertThat(vehicleInspectionsDTO1).isNotEqualTo(vehicleInspectionsDTO2);
        vehicleInspectionsDTO1.setId(null);
        assertThat(vehicleInspectionsDTO1).isNotEqualTo(vehicleInspectionsDTO2);
    }
}
