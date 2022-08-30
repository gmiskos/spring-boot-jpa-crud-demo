package com.demo.sdguiwbe22.boundary;

import com.demo.sdguiwbe22.dto.BeerDTO;
import com.demo.sdguiwbe22.entities.Beer;
import com.demo.sdguiwbe22.entities.BeerType;
import com.demo.sdguiwbe22.service.BeerService;
import com.demo.sdguiwbe22.wrapper.BeerWrapper;
import com.demo.sdguiwbe22.wrapper.RequestWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerBoundary {

    private final ModelMapper modelMapper = new ModelMapper();
    private BeerDTO convertToBeerDto(Beer beer) {
        return modelMapper.map(beer, BeerDTO.class);
    }
    private Beer convertToBeer(BeerDTO beerDTO) {
        Beer beer = modelMapper.map(beerDTO, Beer.class);
        beer.setType(BeerType.fromString(beerDTO.getType()));
        return beer;
    }
    @Autowired
    BeerService beerService;

    public BeerWrapper findAllBeers(int page, int size, boolean sorted){
        BeerWrapper wrapper = new BeerWrapper();

        List<BeerDTO> beers;

        Pageable paging = PageRequest.of(page, size, (sorted)? Sort.by("rate").descending():Sort.by("name").descending());

        Page<Beer> pageCtr;

        pageCtr = beerService.findAllBeers(paging);

        beers = pageCtr.getContent().stream().map(this::convertToBeerDto)
                .collect(Collectors.toList());
        wrapper.setBeers(beers);
        wrapper.setCurrentPage(pageCtr.getNumber());
        wrapper.setTotalItems(pageCtr.getTotalElements());
        wrapper.setTotalPages(pageCtr.getTotalPages());
        return wrapper;
    }

    public BeerDTO saveBeer(BeerDTO beerDTO) {
        Beer beer = beerService.saveBeer(this.convertToBeer(beerDTO));
        return this.convertToBeerDto(beer);
    }

    public Long deleteBeer(Long id) {
        if(beerService.isBeerExist(id))
            beerService.deleteBeer(id);
        if(!beerService.isBeerExist(id))
            return 0L;

        return id;
    }

    public BeerDTO updateBeer(Long id, Integer rate) {
        return this.convertToBeerDto(beerService.updateBeer(id, rate));
    }

    public BeerWrapper searchBeers(RequestWrapper requestWrapper) {
        BeerWrapper wrapper = new BeerWrapper();

        Pageable paging = PageRequest.of(requestWrapper.getPage(), requestWrapper.getSize(), Sort.by("name").ascending().and(Sort.by("rate").descending()));

        wrapper = beerService.searchBeers(requestWrapper.getName(), requestWrapper.getRateFrom(), requestWrapper.getRateTo(), requestWrapper.getType() ,paging);

        return wrapper;
    }
}
