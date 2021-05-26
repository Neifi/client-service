package es.neifi.clientservice.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private UUID clientID;

    private UUID gymID;//TODO receive from registration ms

    private String legalID;

    private String name;

    private String surnames;

    private Date dateOfBirth;

    private String email;

    private String avatar;

    private String street;

    private String postalCode;

    private String city;

    private String state;

    private boolean isActivated;

    private UUID clientAccessKey;
}
