package com.bms.beerservice.web.mappers;

import com.bms.beerservice.domain.Beer;
import com.bms.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beer);
}
