package com.daimlertruck.platform.u20241e281.aftersales.infrastructure.hashing.bcrypt;

import com.daimlertruck.platform.u20241e281.aftersales.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Marker interface for the BCrypt hashing service.
 * It extends both {@link HashingService} and {@link PasswordEncoder}.
 *
 * @author José Antonio Santana Luna
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
