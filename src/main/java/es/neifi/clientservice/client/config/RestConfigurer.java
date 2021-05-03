package es.neifi.clientservice.client.config;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class RestConfigurer implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
    }
}
