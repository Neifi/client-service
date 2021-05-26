package es.neifi.clientservice.client.model;

import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "list", types={ClientEntity.class})
public interface ClientListProjection{

    String getName();
    String getSurnames();
    String getEmail();
    String getLegalID();
    String getDateOfBirth();
    String getAvatar();
}
