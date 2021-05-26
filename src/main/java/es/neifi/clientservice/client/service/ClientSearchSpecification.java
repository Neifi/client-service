package es.neifi.clientservice.client.service;

import es.neifi.clientservice.client.persistence.entity.ClientEntity;

public interface ClientSearchSpecification {
    org.springframework.data.jpa.domain.Specification<ClientEntity> getSpecClientName();

    org.springframework.data.jpa.domain.Specification<ClientEntity> getSpecClientSurname();

    org.springframework.data.jpa.domain.Specification<ClientEntity> getSpecClientLegalId();

    org.springframework.data.jpa.domain.Specification<ClientEntity> getSpecClientEmail();
}
