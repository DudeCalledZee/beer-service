package com.bms.beerservice.bootstrap;

import com.bms.beerservice.domain.Beer;
import com.bms.beerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository repository;

    public BeerLoader(BeerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        loadBeer();
    }

    private void loadBeer() {
        if (repository.count() == 0) {
            repository.save(Beer.builder()
                    .beerName("BrewDog")
                    .beerType("IPA")
                    .quantityToBrew(500)
                    .minOnHand(12)
                    .upc(1354612L)
                    .price(new BigDecimal("5.99"))
                    .build());

            repository.save(Beer.builder()
                    .beerName("Galexy Cat")
                    .beerType("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(4566874489672L)
                    .price(new BigDecimal("11.99"))
                    .build());

            repository.save(Beer.builder()
                    .beerName("Mango Fandango")
                    .beerType("CYDER")
                    .quantityToBrew(100)
                    .minOnHand(11)
                    .upc(6153984L)
                    .price(new BigDecimal("6.99"))
                    .build());
        }
    }
}
