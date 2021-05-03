package es.neifi.clientservice.client.service;


import es.neifi.clientservice.client.dto.ClientDto;
import es.neifi.clientservice.client.dto.ClientDtoConverter;
import es.neifi.clientservice.client.model.Client;
import es.neifi.clientservice.client.repo.ClientRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService extends BaseService<Client, UUID, ClientRepository> {

	@Autowired
	private final ClientRepository clientRepository;
	@Autowired
	private final ClientDtoConverter clientDtoConverter;


	public Page<Client> searchClientByEmail(String email, Pageable pageable) {
		return this.clientRepository.findByEmailContainsIgnoreCase(email, pageable);
	}

//	TODO public List<Cliente> searchByCriteria(String criteria){
//		
//		List<Cliente> clients = findAll();
//		
//
//		List <Cliente> findedClients = clients.parallelStream().filter(c -> c.getNombre().equals(criteria)
//				|| c.getNombre().contains(criteria)
//				|| c.getCalle().contains(criteria)
//				|| c.getCiudad().contains(criteria)
//				|| c.getFecha_nacimiento().contains(criteria)
//				|| c.getApellidos().contains(criteria)
//				|| c.getDni().contains(criteria)
//				|| c.getEmail().contains(criteria)).collect(toList());
//		
//		
//		return findedClients;
//	}

	public Client registerClient(ClientDto cliente) {
		Client clientToBeRegistered = clientDtoConverter.convertToEntity(cliente);
		//int id_gimnasio = clientRepository.findIdGimnasioByIdUsuario(admin.getId());
		//nuevo.setId_gimnasio(id_gimnasio);
		try {
			return save(clientToBeRegistered);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public Client updateClientById(ClientDto cliente, UUID id) {
		Client nuevoCliente = clientDtoConverter.convertToEntity(cliente);
		try {
			findClientById(id);
			return save(nuevoCliente);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public void deleteClientById(UUID id) {
		try {
			findClientById(id);
			deleteClientById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Optional<Client> findClientById(UUID id) {

		return findById(id);
	}

	public Page<Client> findByLegalId(String legal_id, Pageable pageable) {
		return this.repositorio.findByLegalIDContainsIgnoreCase(legal_id, pageable);
	}

	public Page<Client> findByArgs(
			final Optional<String> name, final Optional<String> surnames,
			final Optional<String> legal_id, final Optional<String> email,
			Pageable pageable
	){
		Specification<Client> specClientName = new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(name.isPresent()) {
					return criteriaBuilder
						.like(criteriaBuilder
								.lower(root.get("name")), "%" + name.get() + "%");
				}else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
		};
		Specification<Client> specClientSurname = new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(surnames.isPresent()) {
					return criteriaBuilder
							.like(criteriaBuilder
									.lower(root.get("surnames")), "%" + surnames.get() + "%");
				}else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
		};
		Specification<Client> specClientLegalId = new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(legal_id.isPresent()) {
					return criteriaBuilder
							.like(criteriaBuilder
									.lower(root.get("legalID")), "%" + legal_id.get() + "%");
				}else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
		};

		Specification<Client> specClientEmail = new Specification<Client>() {
			@Override
			public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(email.isPresent()) {
					return criteriaBuilder
							.like(criteriaBuilder
									.lower(root.get("email")), "%" + email.get() + "%");
				}else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
		};

		Specification<Client> all = specClientName.and(specClientSurname).or(specClientEmail).or(specClientLegalId);
		return this.repositorio.findAll(all,pageable);
	}
}
