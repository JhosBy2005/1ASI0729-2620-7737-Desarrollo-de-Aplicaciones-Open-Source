package com.aquamonitor.platform.u20241e281.shared.infrastructure.i18n.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Configures locale resolution for REST requests.
 *
 * <p>The locale is taken from the {@code Accept-Language} header. English is the default
 * language and Spanish is used when the header requests {@code es} or {@code es-PE}.</p>
 */
@Configuration
public class LocaleConfiguration {

    /**
     * Creates the locale resolver based on the {@code Accept-Language} header.
     *
     * @return the locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setSupportedLocales(List.of(Locale.ENGLISH, Locale.forLanguageTag("es")));
        return resolver;
    }
}
