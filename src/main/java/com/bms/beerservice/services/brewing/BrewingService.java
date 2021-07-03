package com.bms.beerservice.services.brewing;

import com.bms.beerservice.config.JmsConfig;
import com.bms.beerservice.domain.Beer;
import com.bms.beerservice.events.BrewBeerEvent;
import com.bms.beerservice.repositories.BeerRepository;
import com.bms.beerservice.services.inventory.BeerInventoryService;
import com.bms.beerservice.web.mappers.BeerMapper;
import com.bms.beerservice.web.model.BeerDto;
import com.bms.beerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {

        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer onHandInventory = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min onHand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + onHandInventory);

            if (beer.getMinOnHand() >= onHandInventory) {
                jmsTemplate.convertAndSend(
                        JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(
                                BeerDto.builder()
                                .id(beer.getId())
                                        .price(beer.getPrice())
                                        .beerStyle(BeerStyleEnum.valueOf(beer.getBeerStyle()))
                                        .beerName(beer.getBeerName())
                                        .version(beer.getVersion().intValue())
                                        .upc(beer.getUpc())
                                        .build()));
            }
        });


    }

}
