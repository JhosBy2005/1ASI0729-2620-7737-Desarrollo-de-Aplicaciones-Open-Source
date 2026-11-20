package com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.entities;

import com.daimlertruck.platform.u20241e281.aftersales.domain.model.valueobjects.DaimlerWorkshopId;
import com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.converters.DaimlerWorkshopIdPersistenceConverter;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.EmailAddress;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PhoneNumber;
import com.daimlertruck.platform.u20241e281.shared.infrastructure.persistence.jpa.converters.EmailAddressPersistenceConverter;
import com.daimlertruck.platform.u20241e281.shared.infrastructure.persistence.jpa.converters.PhoneNumberPersistenceConverter;
import com.daimlertruck.platform.u20241e281.shared.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.daimlertruck.platform.u20241e281.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for authorized workshops.
 *
 * <p>Inherits {@code id}, {@code createdAt} and {@code updatedAt} from
 * {@link AuditableAbstractPersistenceEntity}. Table and column names are
 * resolved to snake case by the shared physical naming strategy.</p>
 *
 * @author José Antonio Santana Luna
 */
@Entity
@Table(name = "authorized_workshops")
@Getter
@Setter
@NoArgsConstructor
public class AuthorizedWorkshopPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = DaimlerWorkshopIdPersistenceConverter.class)
    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private DaimlerWorkshopId workshopId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "manager_first_name", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "manager_last_name", nullable = false))})
    private PersonNamePersistenceEmbeddable managerName;

    @Convert(converter = PhoneNumberPersistenceConverter.class)
    @Column(nullable = false, length = 20)
    private PhoneNumber contactPhone;

    @Convert(converter = EmailAddressPersistenceConverter.class)
    @Column(nullable = false)
    private EmailAddress contactEmail;

    @Column(nullable = false)
    private String workshopLegalName;

    @Column(nullable = false)
    private String city;

    private String provinceOrState;

    @Column(nullable = false)
    private String countryOrRegion;

    @Column(nullable = false, length = 60)
    private String encryptedAuthCode;
}
