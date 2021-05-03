package es.neifi.clientservice.client.controller;

import com.fasterxml.jackson.annotation.JsonView;
import es.neifi.clientservice.client.dto.ClientDto;
import es.neifi.clientservice.client.dto.ClientDtoConverter;
import es.neifi.clientservice.client.dto.TableListInfoDTO;
import es.neifi.clientservice.client.dto.views.ClientView;
import es.neifi.clientservice.client.exception.ClientNotFoundException;
import es.neifi.clientservice.client.exception.PageNotFoundException;
import es.neifi.clientservice.client.model.Client;
import es.neifi.clientservice.client.pagination.PaginationLinksUtils;
import es.neifi.clientservice.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
//@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired private ClientDtoConverter clientDtoConverter;
	//private final TimeRegistryService timeRegistryService;
	//private final StorageService storageService;

	@Autowired
	private PaginationLinksUtils paginationLinksUtils;




	//@PutMapping(value = "user/avatar", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> uploadClientAvatar(/*@AuthenticationPrincipal*/ ClientDto clientDto,
												@RequestPart("file") MultipartFile file) {
		String url = "";
		if (!file.isEmpty()) {
			//url = storeInLocal(file);//TODO rabbitMQ
			//clientDto.setAvatar(url); TODO rabbitMQ
			
			try {
				Optional<Client> clientEntity = clientService.findById(UUID.randomUUID());

				return ResponseEntity.ok(clientService
						.updateClientById(clientDto, clientEntity
								.orElseThrow(ClientNotFoundException::new).getClientID()));
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error uploading the image");
		}

	}


	@JsonView(ClientView.Dto.class)
	@GetMapping(value = "/client")
	public ResponseEntity<?> searchClient(
			@RequestParam("name") Optional<String> name,
			@RequestParam("surnames") Optional<String> surnames,
			@RequestParam("legal_id") Optional<String> legal_id,
			@RequestParam("email") Optional<String> email,
			Pageable pageable, HttpServletRequest request
	){
		Page<Client> result = clientService.findByArgs(name,surnames,legal_id,email,pageable);
		if(result.isEmpty()) throw new ClientNotFoundException();
		return ResponseEntity.ok().header("link",paginationLinksUtils
				.createLinkHeader(result, UriComponentsBuilder
						.fromHttpUrl(request.getRequestURL().toString()))).body(result);
	}


/*	@GetMapping(value = "/client",params = "email")
	public ResponseEntity<?> getClientByEmail(@RequestParam String email,Pageable pageable,HttpServletRequest req) {
		if(email.isEmpty()) throw new ClientNotFoundException();
		Page<Client> client = clientService.searchClientByEmail(email,pageable);
		if (client.isEmpty()) throw new ClientNotFoundException();

		return ResponseEntity.ok().header("link",paginationLinksUtils
				.createLinkHeader(convertDtoToList(client), UriComponentsBuilder
						.fromHttpUrl(req.getRequestURL().toString()))).body(convertDtoToList(client));
	}

	@GetMapping(value = "/client", params = "legal_id")
	public ResponseEntity<?> getClientByLegalId(
			@RequestParam("legal_id") String legalId, Pageable pageable, HttpServletRequest req
	){
		if (legalId.isEmpty()) throw new ClientNotFoundException();
		Page<Client> clients = clientService.findByLegalId(legalId,pageable);
		if(clients.isEmpty()) throw new ClientNotFoundException();

		return ResponseEntity.ok().header("link",paginationLinksUtils
				.createLinkHeader(convertDtoToList(clients), UriComponentsBuilder
						.fromHttpUrl(req.getRequestURL().toString()))).body(convertDtoToList(clients));

	}*/

	/*private String storeInLocal(MultipartFile file) {
		String url;
		String image = storageService.store(file);
		url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "serveFile", image, null).build()
				.toUriString();
		return url;
	}*/

	/*@GetMapping("/client")
	public ResponseEntity<?> getAllClients(@PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request) throws PageNotFoundException {
		Page<Client> clients = clientService.findAll(pageable);
		if (clients.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No clients found");
		} else {
			Page<TableListInfoDTO> dtoList = convertDtoToList(clients);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link"
					,paginationLinksUtils.createLinkHeader(dtoList,uriBuilder)).body(dtoList);
		}

	}*/


	@JsonView(ClientView.Dto.class)
	@GetMapping("/client/{id}")
	public ResponseEntity<?> getOneClient(@PathVariable UUID id) {
		try {

			return ResponseEntity.ok(clientService.findById(id));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@JsonView(ClientView.Dto.class)
	@GetMapping("/client/me")
	public ResponseEntity<?> getCurrentAuthenticatedClient( Client client) {
		try {

			return ResponseEntity.ok(clientService.findById(client.getClientID()));

		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@JsonView(ClientView.Dto.class)
	@PostMapping("/client")
	public ResponseEntity<Client> createClient(@RequestBody ClientDto nuevo) {



		return ResponseEntity.ok(clientService.save(clientDtoConverter.convertToEntity(nuevo)));

	}

	@JsonView(ClientView.Dto.class)
	@PatchMapping("/client")
	public ResponseEntity<?> updateClient(@RequestBody Client client,  Client logedClient) {
		return ResponseEntity.ok(clientService.save(client));

	}

	@JsonView(ClientView.Dto.class)
	@PatchMapping("/client/{id}")
	public Client updateClientById(@RequestBody Client client, @PathVariable UUID id) {
		return clientService.save(client);

	}



	@DeleteMapping("/client/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable UUID id) {
		Client delete = deleteCascade(id);
		clientService.delete(delete);
		return ResponseEntity.ok().build();
	}

	private Client deleteCascade(UUID id) {
		return clientService.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));
		//timeRegistryService.deleteById(id);
	}

}
