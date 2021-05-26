package es.neifi.clientservice.client.persistence.repository;

import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "client")
public interface ClientJpaRepository extends JpaRepository<ClientEntity, UUID>, JpaSpecificationExecutor<ClientEntity> {

	Page<ClientEntity> findByLegalIDContainsIgnoreCase(String legal_id, Pageable pageable);

}
