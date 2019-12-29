package com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class PropertyLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyLoader.class);

    public PropertyLoader() {
    }

    /**
     * Initialisation properties.
     */
    public void loadProperty(Properties properties, String fileName) {
        LOGGER.info("Start initialization properties of {}.", getClass().getName());
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                LOGGER.error("Something went wrong while trying read file: {}", fileName);
                throw new IllegalArgumentException(NULL_WAS_PASSED);
            }
            properties.load(is);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(1);
        }
    }
}
