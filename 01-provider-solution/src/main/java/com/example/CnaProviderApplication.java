package com.example;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class CnaProviderApplication {

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

	public static void main(String[] args) {
		SpringApplication.run(CnaProviderApplication.class, args);
	}
}

@Component
class SimpleCLR implements CommandLineRunner{

	@Autowired ContactRepository contactRepository;

	@Override
	public void run(String... strings) throws Exception {
		Stream.of("Derrick", "Hin", "Sean", "Peter", "James")
				.forEach(name ->  contactRepository.save(new Contact(name, "Hong Kong")));

	}
}

@Entity
@Data @NoArgsConstructor
class Contact{
	@Id
	@GeneratedValue
	Long id;
	String name;
	String address;
	public Contact(String name, String address){
		this.name = name;
		this.address = address;
	}
}

@RestResource
interface ContactRepository extends JpaRepository<Contact, Long>{

}
