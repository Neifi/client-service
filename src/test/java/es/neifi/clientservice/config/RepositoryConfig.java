package es.neifi.clientservice.config;

import es.neifi.clientservice.client.persistence.repository.ClientJpaRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class RepositoryConfig {

    @Bean
    public ClientJpaRepository clientRepository(){
        return Mockito.mock(ClientJpaRepository.class);
    }
}
