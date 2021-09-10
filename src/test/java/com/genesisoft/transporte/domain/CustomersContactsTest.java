package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomersContactsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersContacts.class);
        CustomersContacts customersContacts1 = new CustomersContacts();
        customersContacts1.setId(1L);
        CustomersContacts customersContacts2 = new CustomersContacts();
        customersContacts2.setId(customersContacts1.getId());
        assertThat(customersContacts1).isEqualTo(customersContacts2);
        customersContacts2.setId(2L);
        assertThat(customersContacts1).isNotEqualTo(customersContacts2);
        customersContacts1.setId(null);
        assertThat(customersContacts1).isNotEqualTo(customersContacts2);
    }
}
