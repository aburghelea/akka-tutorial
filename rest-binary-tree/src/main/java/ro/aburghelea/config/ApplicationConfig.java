package ro.aburghelea.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RestController;
import ro.aburghelea.Application;

@Configuration
@ComponentScan(basePackageClasses = Application.class,
        excludeFilters = @Filter({Controller.class, RestController.class, Configuration.class}))
/**
 * Holds the beans necessary for the <strong>Rest Binary Tree</strong>
 * We ignore the component scan for {@link org.springframework.context.annotation.Configuration} class because
 * this class ({@link ro.aburghelea.config.ApplicationConfig}) will be scanned and processed by the
 * {@link ro.aburghelea.config.WebAppInitializer} class. Also {@link org.springframework.stereotype.Controller} and
 * {@link org.springframework.web.bind.annotation.RestController} classes are not scanned, because they are handled
 * by {@link ro.aburghelea.config.WebMvcConfig}
 */
class ApplicationConfig {
	

}