package com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest;

import com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.repositories.AuthorizedWorkshopPersistenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@code /api/v1/workshops} endpoint.
 *
 * @author José Antonio Santana Luna
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorizedWorkshopsControllerTest {

    private static final String ENDPOINT = "/api/v1/workshops";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorizedWorkshopPersistenceRepository persistenceRepository;

    @BeforeEach
    void cleanDatabase() {
        persistenceRepository.deleteAll();
    }

    private static String body(String firstName, String phone, String email, String authCode) {
        return """
                {
                  "managerFirstName": "%s",
                  "managerLastName": "Doe",
                  "contactPhone": "%s",
                  "contactEmail": "%s",
                  "workshopLegalName": "Lima Trucks S.A.C.",
                  "city": "Lima",
                  "provinceOrState": "Lima",
                  "countryOrRegion": "Peru",
                  "maintenanceAuthCode": "%s"
                }
                """.formatted(firstName, phone, email, authCode);
    }

    @Test
    void createsWorkshopAndEncryptsAuthCode() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(body("John", "+51987654321", "john.doe@workshop.com", "AB12CD34")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.workshopId", matchesPattern("[0-9a-f-]{36}")))
                .andExpect(jsonPath("$.managerFirstName").value("John"))
                .andExpect(jsonPath("$.contactPhone").value("+51987654321"))
                .andExpect(jsonPath("$.encryptedAuthCode").doesNotExist())
                .andExpect(jsonPath("$.maintenanceAuthCode").doesNotExist());

        var saved = persistenceRepository.findAll().getFirst();
        assertTrue(new BCryptPasswordEncoder().matches("AB12CD34", saved.getEncryptedAuthCode()));
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void rejectsDuplicatedManagerForSameLegalNameAndCountry() throws Exception {
        var request = body("John", "+51987654321", "john.doe@workshop.com", "AB12CD34");
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated());
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail", containsString("same first and last name")));
    }

    @Test
    void rejectsInvalidEmail() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(body("John", "+51987654321", "not-an-email", "AB12CD34")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", containsString("contactEmail")));
    }

    @Test
    void rejectsInvalidPhoneWithSpanishMessage() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "es-PE")
                        .content(body("John", "987654321", "john.doe@workshop.com", "AB12CD34")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Validacion fallida"))
                .andExpect(jsonPath("$.details", containsString("numero telefonico internacional")));
    }

    @Test
    void rejectsAuthCodeWithWrongLength() throws Exception {
        mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                        .content(body("John", "+51987654321", "john.doe@workshop.com", "123")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", containsString("exactly 8 characters")));
    }
}
