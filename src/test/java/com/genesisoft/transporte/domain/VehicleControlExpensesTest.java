package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlExpensesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlExpenses.class);
        VehicleControlExpenses vehicleControlExpenses1 = new VehicleControlExpenses();
        vehicleControlExpenses1.setId(1L);
        VehicleControlExpenses vehicleControlExpenses2 = new VehicleControlExpenses();
        vehicleControlExpenses2.setId(vehicleControlExpenses1.getId());
        assertThat(vehicleControlExpenses1).isEqualTo(vehicleControlExpenses2);
        vehicleControlExpenses2.setId(2L);
        assertThat(vehicleControlExpenses1).isNotEqualTo(vehicleControlExpenses2);
        vehicleControlExpenses1.setId(null);
        assertThat(vehicleControlExpenses1).isNotEqualTo(vehicleControlExpenses2);
    }
}
