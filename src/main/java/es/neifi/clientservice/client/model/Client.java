package es.neifi.clientservice.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Component
public class Client{

	private static final long serialVersionUID = -5072546529605947104L;

	@Id
	@Column(name = "client_id",insertable = false,updatable = false)
	private UUID clientID = UUID.randomUUID();

	@Column(name = "gym_id")
	private UUID gymID = UUID.randomUUID();//TODO
	@Column(name = "legal_id")
	private String legalID;
	private String name;
	private String surnames;
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	private String email;
	private String avatar;

	@Column(name = "creation_date")
	private LocalDateTime creationDate = LocalDateTime.now();
	private String street;
	@Column(name = "postal_code")
	private String postalCode;
	private String city;
	private String state;
}


