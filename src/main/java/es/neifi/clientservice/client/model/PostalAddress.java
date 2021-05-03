package es.neifi.clientservice.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor

public class PostalAddress implements Serializable {

    private String street;
    private String postal_code;
    private String city;
    private String state;

    public PostalAddress() {
    }
}