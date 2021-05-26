package es.neifi.clientservice.client.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor

public class MailServiceResponse {
    private UUID client_id;
    private boolean isActivated;
    private String reason;
}
