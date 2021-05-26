package es.neifi.clientservice.client.persistence.repository;

import es.neifi.clientservice.client.model.Client;
import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ClientRepository {

    private final ClientJpaRepository clientJpaRepository;

    public Page<Client> getClients(Pageable page){
        try {
            Page<ClientEntity> clientsPage = this.clientJpaRepository.findAll(page);
            return new PageImpl<>(mapToClientList(clientsPage));

        }catch (DataAccessException e){
            log.error(e.getMessage());
        }
        return Page.empty();
    }

    private List<Client> mapToClientList(Page<ClientEntity> clientsPage) {
        return clientsPage.map(this::buildClient)
                .stream().collect(Collectors.toList());

    }

    public Optional<Client> getClient(UUID clientUUID){
        try {

            return this.clientJpaRepository
                    .findById(clientUUID).map(this::buildClient);
        }catch (DataAccessException e){
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    public Client registerClient(Client client){
        try {

            clientJpaRepository.save(buildClientEntity(client));
            return client;
        }catch (DataAccessException e){
            log.error(e.getMessage());
        }

        return null;
    }

    public void deleteClient(UUID clientUUID){
        try {
            this.clientJpaRepository.deleteById(clientUUID);
        }catch (DataAccessException e){
            log.error(e.getMessage());
        }
    }

    public Client updateClient(Client client) {
        try {
            this.clientJpaRepository.save(buildClientEntity(client));
            return client;
        }catch (DataAccessException e){
            log.error(e.getMessage());
        }
        return null;
    }

    private Client buildClient(ClientEntity clientEntity) {
        return Client
                .builder()
                .clientID(clientEntity.getClientID())
                .name(clientEntity.getName())
                .surnames(clientEntity.getSurnames())
                .legalID(clientEntity.getLegalID())
                .dateOfBirth(clientEntity.getDateOfBirth())
                .email(clientEntity.getEmail())
                .avatar(clientEntity.getAvatar())
                .street(clientEntity.getStreet())
                .postalCode(clientEntity.getPostalCode())
                .city(clientEntity.getCity())
                .state(clientEntity.getState())
                .isActivated(clientEntity.isActivated())
                .clientAccessKey(clientEntity.getClientAccessKey())
                .build();
    }
    private ClientEntity buildClientEntity(Client client) {
        return ClientEntity
                .builder()
                .clientID(client.getClientID())
                .name(client.getName())
                .surnames(client.getSurnames())
                .legalID(client.getLegalID())
                .dateOfBirth(client.getDateOfBirth())
                .email(client.getEmail())
                .avatar(client.getAvatar())
                .street(client.getStreet())
                .postalCode(client.getPostalCode())
                .city(client.getCity())
                .state(client.getState())
                .isActivated(client.isActivated())
                .clientAccessKey(client.getClientAccessKey())
                .build();
    }

    public Page<Client> findByLegalIDContainsIgnoreCase(String legal_id, Pageable pageable) {
        return null;
    }

    public Page<Client> getClients(Specification<Client> all, Pageable pageable) {
        return null;
    }
}
