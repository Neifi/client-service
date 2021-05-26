package es.neifi.clientservice.client.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
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
public class ClientEntity {

	private static final long serialVersionUID = -5072546529605947104L;

	@Id
	@Column(name = "client_id",updatable = false)
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	private UUID clientID;

	@Column(name = "gym_id")
	private UUID gymID;//TODO receive from registration ms

	@Column(name = "legal_id",nullable = false)
	private String legalID;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surnames;

	@Column(name = "date_of_birth",nullable = false)
	private Date dateOfBirth;

	@Column(nullable = false)
	private String email;

	private String avatar;

	@Column(nullable = false)
	private String street;

	@Column(name = "postal_code",nullable = false)
	private String postalCode;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private boolean isActivated;

	private UUID clientAccessKey;

	@Column(name = "creation_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}


