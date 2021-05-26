package es.neifi.clientservice;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.Optional.of;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import es.neifi.clientservice.client.model.PaymentApproval;
import es.neifi.clientservice.client.model.dto.ClientDtoConverter;
import es.neifi.clientservice.client.persistence.repository.ClientJpaRepository;
import es.neifi.clientservice.client.service.ClientService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.UUID;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientServiceApplicationTest {
    private final int PORT = 8230;
    private final UUID CLIENT_ID = UUID.randomUUID();
    private static final String TEXT_JSON = "text/json";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);


    private final ClientJpaRepository clientRepository = Mockito.mock(ClientJpaRepository.class);

    @Mock
    ClientDtoConverter clientDtoConverter;

    private final ClientService clientService = new ClientService(clientRepository,clientDtoConverter);

    private final CloseableHttpClient httpClient = HttpClients.createDefault();



    @Test
    public void should_activate_account_when_client_confirm_the_email() throws Exception {
        ClientEntity client = ClientEntity.builder()
                .clientID(UUID.randomUUID())
                .isActivated(false).build();

        JSONObject body = new JSONObject();
        body.put("client_id",client.getClientID());
        body.put("isActivated",true);
        body.put("reason","");

        when(clientRepository.findById(any(UUID.class))).thenReturn(of(client));
        stubFor(get("/mailing-service")
                .willReturn(okJson(body.toString())
                        .withHeader("Content-Type", "application/json")));



        HttpGet request = new HttpGet("http://localhost:+"+PORT+"/mailing-service");
        HttpResponse response = httpClient.execute(request);

        clientRepository.findById(client.getClientID());
        ClientEntity expected = clientService.activateClientAccount(response);


        assertEquals(client.getClientID(),expected.getClientID());
        assertTrue(expected.isActivated());
    }



    @Test
    public void should_deactivate_client_key_when_payment_period_is_reached(){
        ClientEntity client = ClientEntity.builder().clientID(UUID.randomUUID()).build();
        PaymentApproval paymentApproval = PaymentApproval.builder()
                .clientID(client.getClientID())
                .clientIsUpToDate(false).build();

        when(clientRepository.findById(client.getClientID())).thenReturn(of(client));

        ClientEntity expectedClient = clientService.updateAccessKey(paymentApproval);

        assertNull(expectedClient.getClientAccessKey());
    }

    @Test
    public void should_deactivate_client_key_and_account_when_client_unsubscribe(){
        ClientEntity client = ClientEntity.builder().clientID(UUID.randomUUID()).build();

        when(clientRepository.findById(client.getClientID())).thenReturn(of(client));

        ClientEntity expectedClient = clientService.unsubscribe(client.getClientID());

        assertNull(expectedClient.getClientAccessKey());
        assertFalse(expectedClient.isActivated());
    }





}
