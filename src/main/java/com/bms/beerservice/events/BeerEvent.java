package com.bms.beerservice.events;

import com.bms.beerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Builder
@Data
public class BeerEvent implements Serializable {

    public final BeerDto beerDto;
    private final long serialVersionUID = -6143299848725299705L;
}
