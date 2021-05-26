package es.neifi.clientservice.client.model.dto;


import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ClientDtoConverter implements DtoConverter<ClientEntity,ClientDto>{


	private ModelMapper modelMapper;

	public ClientDtoConverter() {
		this.modelMapper = new ModelMapper();
	}

	@Override
	public ClientEntity convertToEntity(ClientDto source) {
		return modelMapper.map(source, ClientEntity.class);
	}

	@Override
	public ClientDto convertToDTO(ClientEntity source) {
		return modelMapper.map(source,ClientDto.class);
	}
}

