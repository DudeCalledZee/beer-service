package com.bms.beerservice.services;

import com.bms.beerservice.domain.Beer;
import com.bms.beerservice.exceptions.NotFoundException;
import com.bms.beerservice.repositories.BeerRepository;
import com.bms.beerservice.web.mappers.BeerMapper;
import com.bms.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getById(UUID uuid) {
        return beerMapper
                .beerToBeerDto(beerRepository
                        .findById(uuid)
                        .orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {

        return beerMapper
                .beerToBeerDto(beerRepository
                        .save(beerMapper
                                .beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {

        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerType(beerDto.getBeerType().toString());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }
}
