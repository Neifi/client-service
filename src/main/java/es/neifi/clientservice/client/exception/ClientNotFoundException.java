package es.neifi.clientservice.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -4672199995633832565L;
	
	public ClientNotFoundException(UUID id) {
		super("No se ha encontrado al cliente con la id "+id);

	}
	public ClientNotFoundException(String message,UUID id) {
		super(message+" id: "+id);
	}
	public ClientNotFoundException() {
		super("No se ha encontrado al cliente");
	}
}
