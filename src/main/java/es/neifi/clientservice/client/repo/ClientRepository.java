package es.neifi.clientservice.client.repo;

import es.neifi.clientservice.client.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {

	

	@Query(value = "SELECT id  AS id FROM Cliente INNER JOIN "
			+ "usuario ON cliente.id = usuario.id WHERE id =:id_usuario",nativeQuery = true)
	int findIdGimnasioByIdUsuario(@Param("id_usuario")int id_usuario);

	Page<Client> findByEmailContainsIgnoreCase(String email,Pageable pageable);
	
	Page<Client> findByLegalIDContainsIgnoreCase(String legal_id,Pageable pageable);


	
}
