package es.neifi.clientservice.client.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PaymentApproval {
    private UUID clientID;
    private boolean clientIsUpToDate;
}
