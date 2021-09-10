package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplierBanksInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierBanksInfo.class);
        SupplierBanksInfo supplierBanksInfo1 = new SupplierBanksInfo();
        supplierBanksInfo1.setId(1L);
        SupplierBanksInfo supplierBanksInfo2 = new SupplierBanksInfo();
        supplierBanksInfo2.setId(supplierBanksInfo1.getId());
        assertThat(supplierBanksInfo1).isEqualTo(supplierBanksInfo2);
        supplierBanksInfo2.setId(2L);
        assertThat(supplierBanksInfo1).isNotEqualTo(supplierBanksInfo2);
        supplierBanksInfo1.setId(null);
        assertThat(supplierBanksInfo1).isNotEqualTo(supplierBanksInfo2);
    }
}
