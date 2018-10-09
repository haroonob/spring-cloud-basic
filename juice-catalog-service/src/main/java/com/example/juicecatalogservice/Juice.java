package com.example.juicecatalogservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Juice {
    public Juice(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
@RepositoryRestResource
interface JuiceRepository extends JpaRepository<Juice, Long> {}
@Component
class JuiceInitializer implements CommandLineRunner {

    private final JuiceRepository juiceRepository;

    JuiceInitializer(JuiceRepository juiceRepository) {
        this.juiceRepository = juiceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Kentucky Brunch Brand Stout", "Good Morning", "Very Hazy", "King Julius",
                "Budweiser", "Coors Light", "PBR")
                .forEach(juice -> juiceRepository.save(new Juice(juice)));

        juiceRepository.findAll().forEach(System.out::println);
    }
}


