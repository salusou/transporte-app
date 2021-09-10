package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleControlBillingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleControlBilling.class);
        VehicleControlBilling vehicleControlBilling1 = new VehicleControlBilling();
        vehicleControlBilling1.setId(1L);
        VehicleControlBilling vehicleControlBilling2 = new VehicleControlBilling();
        vehicleControlBilling2.setId(vehicleControlBilling1.getId());
        assertThat(vehicleControlBilling1).isEqualTo(vehicleControlBilling2);
        vehicleControlBilling2.setId(2L);
        assertThat(vehicleControlBilling1).isNotEqualTo(vehicleControlBilling2);
        vehicleControlBilling1.setId(null);
        assertThat(vehicleControlBilling1).isNotEqualTo(vehicleControlBilling2);
    }
}
