package es.neifi.clientservice.client.model;

import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "postalAddress", types = {ClientEntity.class})
public interface ClientPostalAddressProjection {
    String getStreet();
    String getCity();
    String getState();
    String getPostalCode();
}
