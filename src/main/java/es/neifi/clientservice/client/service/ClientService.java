package es.neifi.clientservice.client.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.neifi.clientservice.client.model.Client;
import es.neifi.clientservice.client.model.dto.ClientDto;
import es.neifi.clientservice.client.model.dto.DtoConverter;
import es.neifi.clientservice.client.exception.ClientAccessKeyException;
import es.neifi.clientservice.client.exception.ClientNotFoundException;
import es.neifi.clientservice.client.persistence.entity.Client;
import es.neifi.clientservice.client.model.MailServiceResponse;
import es.neifi.clientservice.client.model.PaymentApproval;
import es.neifi.clientservice.client.persistence.repository.ClientJpaRepository;
import es.neifi.clientservice.client.persistence.repository.ClientRepository;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService  {

	@Autowired
	private final ClientRepository clientRepository;


	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Client registerClient(Client client) {
		return this.clientRepository.registerClient(client);
	}


	public Page<Client> findByLegalId(String legal_id, Pageable pageable) {
		return this.clientRepository.findByLegalIDContainsIgnoreCase(legal_id, pageable);
	}

	public Page<Client> findByArgs(final Optional<String> name, final Optional<String> surnames,
										 final Optional<String> legal_id, final Optional<String> email,
										 Pageable pageable){
		ClientSearchSpecification clientSearchSpecification = new
				ClientSearchSpecificationImpl(name,surnames,legal_id,email,pageable);

		Specification<Client> all = clientSearchSpecification
				.getSpecClientName()
				.and(clientSearchSpecification.getSpecClientSurname())
				.or(clientSearchSpecification.getSpecClientEmail())
				.or(clientSearchSpecification.getSpecClientLegalId());

		return this.clientRepository.getClients(all,pageable);
	}


	public Client activateClientAccount(HttpResponse response) {
		StringBuilder jsonClient = new StringBuilder();
		ObjectMapper mapper = new ObjectMapper();

		try {
			jsonClient.append(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
			MailServiceResponse mailServiceResponse = mapToMailServiceResponse(jsonClient, mapper);

			Client clientToActivate = getClientToActivate(mailServiceResponse.getClient_id());

			clientToActivate.setActivated(true);
			return clientToActivate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Client getClientToActivate(UUID uuid) {
		return this.clientRepository.findById(uuid)
				.orElseThrow(
						() -> new ClientNotFoundException(
								"Client not found during the account activation"
								, uuid));
	}


	private MailServiceResponse mapToMailServiceResponse(StringBuilder jsonClient, ObjectMapper mapper) throws JsonProcessingException {
		Map<String,Object> mapRes = mapper.readValue(jsonClient.toString(),Map.class);
		MailServiceResponse mailServiceResponse = new MailServiceResponse();
		mailServiceResponse.setClient_id(UUID.fromString((String) mapRes.get("client_id")));
		mailServiceResponse.setReason((String) mapRes.get("reason"));
		mailServiceResponse.setActivated(((boolean)mapRes.get("isActivated")));
		return mailServiceResponse;
	}

	@RabbitListener(queues="")
	public Client updateAccessKey(PaymentApproval paymentApproval) throws ClientAccessKeyException {
		Client clientToActivateKey = getClientToActivate(paymentApproval.getClientID());

		if(paymentApproval.isClientIsUpToDate()){

			if (clientToActivateKey.getClientAccessKey() == null) {
				UUID clientAccessKey = generateAccessKey();
				clientToActivateKey.setClientAccessKey(clientAccessKey);
			}

			return this.clientRepository.save(clientToActivateKey);
		}else{
			clientToActivateKey.setClientAccessKey(null);

			return clientToActivateKey;
		}
	}


	private UUID generateAccessKey() {

		return UUID.randomUUID();
	}


	public Client unsubscribe(UUID clientID) {
		Client clientToUnsubscribe = this.clientRepository
				.findById(clientID).orElseThrow(ClientNotFoundException::new);

		clientToUnsubscribe.setActivated(false);
		clientToUnsubscribe.setClientAccessKey(null);
		this.clientRepository.save(clientToUnsubscribe);
		return clientToUnsubscribe;
	}
}
