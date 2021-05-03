package es.neifi.clientservice.client.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "postalAddress", types = {Client.class})
public interface ClientPostalAddressProjection {
    String getStreet();
    String getCity();
    String getState();
    String getPostalCode();
}
