package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplierContactsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierContacts.class);
        SupplierContacts supplierContacts1 = new SupplierContacts();
        supplierContacts1.setId(1L);
        SupplierContacts supplierContacts2 = new SupplierContacts();
        supplierContacts2.setId(supplierContacts1.getId());
        assertThat(supplierContacts1).isEqualTo(supplierContacts2);
        supplierContacts2.setId(2L);
        assertThat(supplierContacts1).isNotEqualTo(supplierContacts2);
        supplierContacts1.setId(null);
        assertThat(supplierContacts1).isNotEqualTo(supplierContacts2);
    }
}
