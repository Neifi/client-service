package es.neifi.clientservice.client.dto;

import com.fasterxml.jackson.annotation.JsonView;
import es.neifi.clientservice.client.dto.views.ClientView;
import es.neifi.clientservice.client.model.PostalAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class ClientDto {

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
	private String password;
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

