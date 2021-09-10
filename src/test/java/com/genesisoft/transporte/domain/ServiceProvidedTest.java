package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceProvidedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceProvided.class);
        ServiceProvided serviceProvided1 = new ServiceProvided();
        serviceProvided1.setId(1L);
        ServiceProvided serviceProvided2 = new ServiceProvided();
        serviceProvided2.setId(serviceProvided1.getId());
        assertThat(serviceProvided1).isEqualTo(serviceProvided2);
        serviceProvided2.setId(2L);
        assertThat(serviceProvided1).isNotEqualTo(serviceProvided2);
        serviceProvided1.setId(null);
        assertThat(serviceProvided1).isNotEqualTo(serviceProvided2);
    }
}
