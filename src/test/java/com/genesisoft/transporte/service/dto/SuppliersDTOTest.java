package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SuppliersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuppliersDTO.class);
        SuppliersDTO suppliersDTO1 = new SuppliersDTO();
        suppliersDTO1.setId(1L);
        SuppliersDTO suppliersDTO2 = new SuppliersDTO();
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
        suppliersDTO2.setId(suppliersDTO1.getId());
        assertThat(suppliersDTO1).isEqualTo(suppliersDTO2);
        suppliersDTO2.setId(2L);
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
        suppliersDTO1.setId(null);
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
    }
}
