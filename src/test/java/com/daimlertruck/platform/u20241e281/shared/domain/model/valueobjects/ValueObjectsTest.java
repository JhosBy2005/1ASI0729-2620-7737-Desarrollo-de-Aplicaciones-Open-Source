package com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the shared value objects.
 *
 * @author José Antonio Santana Luna
 */
class ValueObjectsTest {

    @Test
    void emailAddressAcceptsValidFormat() {
        assertEquals("john.doe@workshop.com", new EmailAddress("john.doe@workshop.com").address());
    }

    @Test
    void emailAddressRejectsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new EmailAddress("john.doe-at-workshop"));
    }

    @Test
    void phoneNumberAcceptsInternationalFormat() {
        assertEquals("+51987654321", new PhoneNumber("+51987654321").number());
    }

    @Test
    void phoneNumberRejectsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("987654321"));
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("+51 98-ABC"));
    }

    @Test
    void personNameRequiresFirstAndLastName() {
        assertEquals("John Doe", new PersonName("John", "Doe").getFullName());
        assertThrows(IllegalArgumentException.class, () -> new PersonName("John", " "));
    }
}
