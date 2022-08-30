package com.demo.sdguiwbe22.service;

import com.demo.sdguiwbe22.entities.Beer;
import com.demo.sdguiwbe22.wrapper.BeerWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeerService {

    Page<Beer> findAllBeers(Pageable paging);
    Beer saveBeer(Beer beer);
    void deleteBeer(Long id);

    boolean isBeerExist(Long id);
    Beer updateBeer(Long id, Integer rate);

    BeerWrapper searchBeers(String name, Integer rateFrom, Integer rateTo, String type, Pageable paging);
}
