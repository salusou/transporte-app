package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplierContactsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierContactsDTO.class);
        SupplierContactsDTO supplierContactsDTO1 = new SupplierContactsDTO();
        supplierContactsDTO1.setId(1L);
        SupplierContactsDTO supplierContactsDTO2 = new SupplierContactsDTO();
        assertThat(supplierContactsDTO1).isNotEqualTo(supplierContactsDTO2);
        supplierContactsDTO2.setId(supplierContactsDTO1.getId());
        assertThat(supplierContactsDTO1).isEqualTo(supplierContactsDTO2);
        supplierContactsDTO2.setId(2L);
        assertThat(supplierContactsDTO1).isNotEqualTo(supplierContactsDTO2);
        supplierContactsDTO1.setId(null);
        assertThat(supplierContactsDTO1).isNotEqualTo(supplierContactsDTO2);
    }
}
