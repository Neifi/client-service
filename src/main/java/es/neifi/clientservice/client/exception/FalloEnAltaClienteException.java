package es.neifi.clientservice.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FalloEnAltaClienteException extends RuntimeException{

	private static final long serialVersionUID = -4672199995633832565L;
	
	public FalloEnAltaClienteException() {
		super("No se ha podido dar de alta al cliente");
		 
	}
	
	public FalloEnAltaClienteException(RuntimeException ex) {
		super("No se ha podido dar de alta al cliente");
		 throw new RuntimeException(ex);
	}
}
