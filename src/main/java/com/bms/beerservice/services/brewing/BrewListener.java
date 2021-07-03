package com.bms.beerservice.services.brewing;

import com.bms.beerservice.config.JmsConfig;
import com.bms.beerservice.domain.Beer;
import com.bms.beerservice.events.BrewBeerEvent;
import com.bms.beerservice.events.NewInventoryEvent;
import com.bms.beerservice.repositories.BeerRepository;
import com.bms.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class BrewListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        BeerDto beerDto = event.getBeerDto();

        Beer oneBeer = beerRepository.getOne(beerDto.getId());

        beerDto.setQualityOnHand(oneBeer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewing Beer " + oneBeer.getMinOnHand() + " : QOH: " + beerDto.getQualityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);

    }
}
