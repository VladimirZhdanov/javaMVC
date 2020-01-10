package com.foxminded.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

import static com.foxminded.business.constants.Constants.CHARACTER_ENCODING;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Configuration
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public WebAppInitializer() {
        super();
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                SpringConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "/"
        };
    }

    @Override
    protected Filter[] getServletFilters() {
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding(CHARACTER_ENCODING);
        encodingFilter.setForceEncoding(true);
        return new Filter[] {encodingFilter};
    }
}
