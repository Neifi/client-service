package es.neifi.clientservice.client.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import es.neifi.clientservice.client.model.dto.views.ClientView;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
@Builder
public class ClientDto {

	@JsonView(ClientView.Dto.class)
	private UUID clientID;
	@JsonView(ClientView.Dto.class)
	private String legalID;
	@JsonView(ClientView.Dto.class)
	private String name;
	@JsonView(ClientView.Dto.class)
	private String surnames;
	@JsonView(ClientView.Dto.class)
	private String dateOfBirth;
	@JsonView(ClientView.Dto.class)
	private String email;
	@JsonView(ClientView.Dto.class)
	private String avatar;
	@JsonView(ClientView.Dto.class)
	private String street;
	@JsonView(ClientView.Dto.class)
	private String postalCode;
	@JsonView(ClientView.Dto.class)
	private String city;
	@JsonView(ClientView.Dto.class)
	private String state;
}	

