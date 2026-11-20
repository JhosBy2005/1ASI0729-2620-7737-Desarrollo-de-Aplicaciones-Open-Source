package com.daimlertruck.platform.u20241e281.aftersales.infrastructure.hashing.bcrypt.services;

import com.daimlertruck.platform.u20241e281.aftersales.infrastructure.hashing.bcrypt.BCryptHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * BCrypt implementation of the hashing service, used to encrypt
 * the maintenance authorization code of authorized workshops.
 *
 * @author José Antonio Santana Luna
 */
@Service
public class HashingServiceImpl implements BCryptHashingService {

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor.
     */
    public HashingServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Hashes a raw value using BCrypt.
     *
     * @param rawValue the raw value
     * @return the BCrypt hash
     */
    @Override
    public String encode(CharSequence rawValue) {
        return passwordEncoder.encode(rawValue);
    }

    /**
     * Checks whether a raw value matches a BCrypt hash.
     *
     * @param rawValue     the raw value
     * @param encodedValue the BCrypt hash
     * @return {@code true} when they match
     */
    @Override
    public boolean matches(CharSequence rawValue, String encodedValue) {
        return passwordEncoder.matches(rawValue, encodedValue);
    }
}
