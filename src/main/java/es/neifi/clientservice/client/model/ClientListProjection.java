package es.neifi.clientservice.client.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "list", types={Client.class})
public interface ClientListProjection{

    String getName();
    String getSurnames();
    String getEmail();
    String getLegalID();
    String getDateOfBirth();
    String getAvatar();
}
