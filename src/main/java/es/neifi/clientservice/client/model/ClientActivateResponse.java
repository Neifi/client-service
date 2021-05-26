package es.neifi.clientservice.client.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
public class ClientActivateResponse {
    private UUID clientID;
    private boolean isActivated;
    private String statusMessage;
}
