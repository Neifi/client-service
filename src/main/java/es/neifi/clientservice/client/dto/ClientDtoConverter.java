package es.neifi.clientservice.client.dto;


import es.neifi.clientservice.client.model.Client;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoConverter {

	@Autowired
	private ModelMapper modelMapper;
	

	public Client convertToEntity(ClientDto dto) {
		return modelMapper.map(dto,Client.class);
	}
	
	public TableListInfoDTO convertToDto(Client cliente) {
		return modelMapper.map(cliente,TableListInfoDTO.class);
	}



}	

