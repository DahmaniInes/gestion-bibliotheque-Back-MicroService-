package tn.esprit.BlogMs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BlogMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogMsApplication.class, args);
	}

}
