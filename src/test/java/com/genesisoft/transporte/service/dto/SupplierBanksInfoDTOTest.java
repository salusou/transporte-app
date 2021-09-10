package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplierBanksInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierBanksInfoDTO.class);
        SupplierBanksInfoDTO supplierBanksInfoDTO1 = new SupplierBanksInfoDTO();
        supplierBanksInfoDTO1.setId(1L);
        SupplierBanksInfoDTO supplierBanksInfoDTO2 = new SupplierBanksInfoDTO();
        assertThat(supplierBanksInfoDTO1).isNotEqualTo(supplierBanksInfoDTO2);
        supplierBanksInfoDTO2.setId(supplierBanksInfoDTO1.getId());
        assertThat(supplierBanksInfoDTO1).isEqualTo(supplierBanksInfoDTO2);
        supplierBanksInfoDTO2.setId(2L);
        assertThat(supplierBanksInfoDTO1).isNotEqualTo(supplierBanksInfoDTO2);
        supplierBanksInfoDTO1.setId(null);
        assertThat(supplierBanksInfoDTO1).isNotEqualTo(supplierBanksInfoDTO2);
    }
}
