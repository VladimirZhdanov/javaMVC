package com.foxminded.web.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class DateFormatter implements Formatter<LocalDateTime> {

    @Autowired
    private MessageSource messageSource;


    public DateFormatter() {
        super();
    }

    /**
     * Parses text to LocalDataTame.
     *
     * @param text - text
     * @param locale - locale
     * @return - LocalDateTime
     */
    public LocalDateTime parse(final String text, final Locale locale) {
        final DateTimeFormatter dateFormat = createDateFormat(locale);
        return LocalDateTime.parse(text, dateFormat);
    }

    /**
     * Creates date time formatter by given locale.
     *
     * @param locale - local
     * @return - DateTimeFormatter
     */
    private DateTimeFormatter createDateFormat(final Locale locale) {
        final String format = this.messageSource.getMessage("date.format", null, locale);
        return DateTimeFormatter.ofPattern(format);
    }

    /**
     * Return string view of given localDateTime and local.
     *
     * @param localDateTime - localDateTime
     * @param locale - locale
     * @return - String
     */
    @Override
    public String print(final LocalDateTime localDateTime, final Locale locale) {
        final DateTimeFormatter dateFormat = createDateFormat(locale);
        return dateFormat.format(localDateTime);
    }
}
