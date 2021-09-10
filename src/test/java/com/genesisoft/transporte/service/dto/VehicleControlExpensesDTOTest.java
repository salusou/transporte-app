package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlExpensesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlExpensesDTO.class);
        VehicleControlExpensesDTO vehicleControlExpensesDTO1 = new VehicleControlExpensesDTO();
        vehicleControlExpensesDTO1.setId(1L);
        VehicleControlExpensesDTO vehicleControlExpensesDTO2 = new VehicleControlExpensesDTO();
        assertThat(vehicleControlExpensesDTO1).isNotEqualTo(vehicleControlExpensesDTO2);
        vehicleControlExpensesDTO2.setId(vehicleControlExpensesDTO1.getId());
        assertThat(vehicleControlExpensesDTO1).isEqualTo(vehicleControlExpensesDTO2);
        vehicleControlExpensesDTO2.setId(2L);
        assertThat(vehicleControlExpensesDTO1).isNotEqualTo(vehicleControlExpensesDTO2);
        vehicleControlExpensesDTO1.setId(null);
        assertThat(vehicleControlExpensesDTO1).isNotEqualTo(vehicleControlExpensesDTO2);
    }
}
