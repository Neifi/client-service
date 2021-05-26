package es.neifi.clientservice.client.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter @Setter
public class TableListInfoDTO {

	private String legalID;
	private String name;
	private String surnames;
	private Date dateOfBirth;
	private String email;
	private String avatar;

	private LocalDateTime creationDate = LocalDateTime.now();
	private String street;
	private String postalCode;
	private String city;
	private String state;

}
