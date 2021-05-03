package es.neifi.clientservice;

import es.neifi.clientservice.client.controller.ClientController;
import es.neifi.clientservice.client.dto.ClientDtoConverter;
import es.neifi.clientservice.client.service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.es.neifi.clientservice.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}
}
