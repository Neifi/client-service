package es.neifi.clientservice.client.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ClientAccessKey {
    private UUID id;
    private boolean isEnabled;
}
