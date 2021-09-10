package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceProvidedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceProvidedDTO.class);
        ServiceProvidedDTO serviceProvidedDTO1 = new ServiceProvidedDTO();
        serviceProvidedDTO1.setId(1L);
        ServiceProvidedDTO serviceProvidedDTO2 = new ServiceProvidedDTO();
        assertThat(serviceProvidedDTO1).isNotEqualTo(serviceProvidedDTO2);
        serviceProvidedDTO2.setId(serviceProvidedDTO1.getId());
        assertThat(serviceProvidedDTO1).isEqualTo(serviceProvidedDTO2);
        serviceProvidedDTO2.setId(2L);
        assertThat(serviceProvidedDTO1).isNotEqualTo(serviceProvidedDTO2);
        serviceProvidedDTO1.setId(null);
        assertThat(serviceProvidedDTO1).isNotEqualTo(serviceProvidedDTO2);
    }
}
