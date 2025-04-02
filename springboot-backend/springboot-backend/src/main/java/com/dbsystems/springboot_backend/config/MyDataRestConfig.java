package com.dbsystems.springboot_backend.config;

import com.dbsystems.springboot_backend.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.http.HttpMethod;


@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrigins = "http://localhost:3000"; // Adjust this to your frontend URL

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Customize the base path for REST endpoints
        config.setBasePath("/api");
        
        // Optionally, you can customize other settings here
        // For example, you can set the default media type or enable CORS
        HttpMethod[] theUnsupportedActions = {
            HttpMethod.POST,
            HttpMethod.PUT,
            HttpMethod.DELETE,
            HttpMethod.PATCH};

        config.exposeIdsFor(User.class);
        disableHttpMethods(User.class, config, theUnsupportedActions);

        /* Configure CORS Mapping*/
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins);

    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        // Disable HTTP methods for specific entity types
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }
    
}
