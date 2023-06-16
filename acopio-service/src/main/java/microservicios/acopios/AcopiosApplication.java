package microservicios.acopios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AcopiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcopiosApplication.class, args);
	}

}
