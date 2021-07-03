package com.bms.beerservice.repositories;

import com.bms.beerservice.domain.Beer;
import com.bms.beerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);

    Page<Beer> findAllByBeerName(String beerName, PageRequest pageRequest);

    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, PageRequest pageRequest);

    Beer findBeerByUpc(String upc);
}
