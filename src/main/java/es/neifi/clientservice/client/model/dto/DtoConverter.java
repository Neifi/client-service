package es.neifi.clientservice.client.model.dto;

public interface DtoConverter<S,D> {

    S convertToEntity(D source);
    D convertToDTO(S source);


}
