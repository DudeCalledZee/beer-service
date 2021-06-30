package com.bms.beerservice.web.model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class BeerPageList extends PageImpl<BeerDto> implements Serializable {

    static final long serialVersionUID = -876093766114244578L;

    public BeerPageList(List<BeerDto> content,
                        int number,
                        int size,
                        Long totalElements,
                        JsonNode pageable,
                        boolean last,
                        int totalPages,
                        JsonNode sort,
                        boolean first,
                        int numberOfElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public BeerPageList(List<BeerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerPageList(List<BeerDto> content) {
        super(content);
    }
}
