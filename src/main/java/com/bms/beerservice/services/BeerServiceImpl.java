package com.bms.beerservice.services;

import com.bms.beerservice.domain.Beer;
import com.bms.beerservice.exceptions.NotFoundException;
import com.bms.beerservice.repositories.BeerRepository;
import com.bms.beerservice.web.mappers.BeerMapper;
import com.bms.beerservice.web.model.BeerDto;
import com.bms.beerservice.web.model.BeerPageList;
import com.bms.beerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getById(UUID uuid, Boolean showInventoryOnHand) {
        if(showInventoryOnHand) {
            return beerMapper
                    .beerToBeerDtoWithInventory(beerRepository
                            .findById(uuid)
                            .orElseThrow(NotFoundException::new));
        } else {
            return beerMapper
                    .beerToBeerDto(beerRepository
                            .findById(uuid)
                            .orElseThrow(NotFoundException::new));
        }

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
        beer.setBeerStyle(beerDto.getBeerStyle().toString());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    public BeerPageList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPageList beerPageList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {

            beerPageList = new BeerPageList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        } else {

            beerPageList = new BeerPageList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        }
            return beerPageList;
    }
}
