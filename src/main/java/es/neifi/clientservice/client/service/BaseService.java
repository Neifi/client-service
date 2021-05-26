package es.neifi.clientservice.client.service;

import es.neifi.clientservice.client.exception.ClientAccessKeyException;
import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import es.neifi.clientservice.client.model.PaymentApproval;
import es.neifi.clientservice.client.model.dto.*;
import org.apache.http.HttpResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface BaseService {
    ClientEntity registerClient(ClientDto cliente);

    Page<ClientEntity> findByLegalId(String legal_id, Pageable pageable);

    Page<ClientEntity> findByArgs(Optional<String> name, Optional<String> surnames,
                                  Optional<String> legal_id, Optional<String> email,
                                  Pageable pageable);

    ClientEntity activateClientAccount(HttpResponse response);

    @RabbitListener(queues = "")
    ClientEntity updateAccessKey(PaymentApproval paymentApproval) throws ClientAccessKeyException;

    ClientEntity unsubscribe(UUID clientID);
}
