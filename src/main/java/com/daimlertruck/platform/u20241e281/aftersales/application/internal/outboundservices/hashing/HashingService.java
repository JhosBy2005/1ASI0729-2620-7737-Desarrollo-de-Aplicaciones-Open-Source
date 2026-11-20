package com.daimlertruck.platform.u20241e281.aftersales.application.internal.outboundservices.hashing;

/**
 * Outbound port for hashing operations required by the after-sales application layer.
 *
 * @author José Antonio Santana Luna
 */
public interface HashingService {

    /**
     * Encodes a raw value for persistence.
     *
     * @param rawValue the raw value
     * @return the encoded value
     */
    String encode(CharSequence rawValue);

    /**
     * Verifies whether a raw value matches an encoded value.
     *
     * @param rawValue     the raw value
     * @param encodedValue the encoded value
     * @return {@code true} when both values match
     */
    boolean matches(CharSequence rawValue, String encodedValue);
}
