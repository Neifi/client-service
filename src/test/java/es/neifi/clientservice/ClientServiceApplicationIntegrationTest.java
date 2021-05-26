package es.neifi.clientservice;

import static org.junit.jupiter.api.Assertions.assertFalse;

import es.neifi.clientservice.client.model.dto.ClientDto;
import es.neifi.clientservice.client.model.dto.ClientDtoConverter;
import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import es.neifi.clientservice.client.model.PaymentApproval;
import es.neifi.clientservice.client.persistence.repository.ClientJpaRepository;
import es.neifi.clientservice.client.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@SpringBootTest
public class ClientServiceApplicationIntegrationTest {


    @Autowired
    private ClientJpaRepository clientRepository;

    ClientDtoConverter clientDtoConverter;

    private ClientService clientService;


    @BeforeEach
    public void setup(){
        this.clientDtoConverter = new ClientDtoConverter();
        this.clientService = new ClientService(clientRepository,clientDtoConverter);
    }


    @Test
    public void should_get_client_by_args(){

        Optional<String> name = Optional.of("testName");
        Optional<String> email = Optional.of("test@test.com");
        Optional<String> legalID = Optional.of("12345678Y");
        Optional<String> surname = Optional.of("testSurname");
        final Optional<String> EMPTY = Optional.empty();
        final Pageable UNPAGED = Pageable.unpaged();
        ClientDto clientDto = ClientDto.builder()
                .email(email.get())
                .name(name.get())
                .surnames(surname.get())
                .legalID(legalID.get())
                .build();

        ClientEntity expectedClient = clientService.registerClient(clientDto);

        Page<ClientEntity> clientByName = clientService.findByArgs(name,EMPTY,EMPTY,EMPTY, UNPAGED);
        Page<ClientEntity> clientByLastname = clientService.findByArgs(EMPTY,surname,EMPTY,email, UNPAGED);
        Page<ClientEntity> clientByLegalID = clientService.findByArgs(EMPTY,EMPTY,legalID,EMPTY, UNPAGED);
        Page<ClientEntity> clientByEmail = clientService.findByArgs(EMPTY,EMPTY,EMPTY,email, UNPAGED);
        Page<ClientEntity> clientByAll = clientService.findByArgs(name,surname,legalID,email, UNPAGED);
        Page<ClientEntity> emptyClient = clientService.findByArgs(surname,name,surname,surname, UNPAGED);

        assertFalse(clientByName.isEmpty());
        assertFalse(clientByLastname.isEmpty());
        assertFalse(clientByLegalID.isEmpty());
        assertFalse(clientByEmail.isEmpty());
        Assertions.assertTrue(emptyClient.isEmpty());

        Assertions.assertEquals(expectedClient,clientByName.getContent().get(0));
        Assertions.assertEquals(expectedClient,clientByLastname.getContent().get(0));
        Assertions.assertEquals(expectedClient,clientByLegalID.getContent().get(0));
        Assertions.assertEquals(expectedClient,clientByEmail.getContent().get(0));
        Assertions.assertEquals(expectedClient,clientByAll.getContent().get(0));


    }

    @Test
    public void should_activate_client_key_when_payment_is_received() {
        Optional<String> name = Optional.of("testName");
        Optional<String> email = Optional.of("test@test.com");
        Optional<String> legalID = Optional.of("12345678Y");
        Optional<String> surname = Optional.of("testSurname");

        ClientDto clientDto = ClientDto.builder()
                .email(email.get())
                .name(name.get())
                .surnames(surname.get())
                .legalID(legalID.get())
                .build();
        ClientEntity client = clientService.registerClient(clientDto);

        PaymentApproval paymentApproval = PaymentApproval.builder()
                .clientID(client.getClientID())
                .clientIsUpToDate(true).build();

        ClientEntity expectedClient = clientService.updateAccessKey(paymentApproval);

        Assertions.assertNotNull(expectedClient.getClientAccessKey());

    };





}
