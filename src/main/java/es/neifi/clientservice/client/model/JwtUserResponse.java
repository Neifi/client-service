package es.neifi.clientservice.client.model;

import es.neifi.clientservice.client.dto.ClientDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

/**
 * Al generarse el token envia los datos del usuario logeado y el token
 * @author neifi
 *
 */
public class JwtUserResponse extends ClientDto {
	private String token;
	
	@Builder(builderMethodName = "jwtUserResponseBuilder")
	public JwtUserResponse(Set<String>rol, String token) {
		
		this.token = token;
	}
}


