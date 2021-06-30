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

    public static String BEER_1_UPC = "061234056467";
    public static String BEER_2_UPC = "062348431575";
    public static String BEER_3_UPC = "068794987124";

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
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("5.99"))
                    .build());

            repository.save(Beer.builder()
                    .beerName("Galexy Cat")
                    .beerType("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("11.99"))
                    .build());

            repository.save(Beer.builder()
                    .beerName("Mango Fandango")
                    .beerType("CYDER")
                    .quantityToBrew(100)
                    .minOnHand(11)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("6.99"))
                    .build());
        }
    }
}
