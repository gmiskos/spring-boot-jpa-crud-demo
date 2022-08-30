package com.demo.sdguiwbe22.service.impl;

import com.demo.sdguiwbe22.dto.BeerDTO;
import com.demo.sdguiwbe22.entities.Beer;
import com.demo.sdguiwbe22.entities.BeerType;
import com.demo.sdguiwbe22.repository.BeerRepository;
import com.demo.sdguiwbe22.service.BeerService;
import com.demo.sdguiwbe22.wrapper.BeerWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Primary
public class BeerServiceImpl implements BeerService {

    @Autowired
    BeerRepository beerRepository;

    private final EntityManager entityManager;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Page<Beer> findAllBeers(Pageable paging) {
        return beerRepository.findAll(paging);
    }

    @Override
    public Beer saveBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public void deleteBeer(Long id) {
        beerRepository.delete(beerRepository.findById(id).get());
    }

    @Override
    public boolean isBeerExist(Long id) {
        return beerRepository.existsById(id);
    }

    @Override
    public Beer updateBeer(Long id, Integer rate) {
        Beer beerToUpdate = beerRepository.findById(id).get();
        beerToUpdate.setRate(rate);
        return beerRepository.save(beerToUpdate);
    }

    @Override
    public BeerWrapper searchBeers(String name, Integer rateFrom, Integer rateTo, String type, Pageable paging) {
        BeerWrapper wrapper = new BeerWrapper();

        //count
        CriteriaBuilder countCriteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countCriteriaBuilder.createQuery(Long.class);
        Root<Beer> root = countQuery.from(Beer.class);
        countQuery.select(countCriteriaBuilder.count(root));

        //data
        CriteriaBuilder dataCriteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Beer> dataQuery = dataCriteriaBuilder.createQuery(Beer.class);
        Root<Beer> beers = dataQuery.from(Beer.class);

        //filtesr
        List<Predicate> countPredicates = new ArrayList<>();
        List<Predicate> dataPredicates = new ArrayList<>();
        if (name != null) {
            dataPredicates.add(dataCriteriaBuilder.equal(beers.get("name"), name));
            countPredicates.add(countCriteriaBuilder.equal(root.get("name"), name));
        }
        if (type != null) {
            dataPredicates.add(dataCriteriaBuilder.equal(beers.get("type"), BeerType.fromString(type)));
            countPredicates.add(countCriteriaBuilder.equal(root.get("type"), BeerType.fromString(type)));
        }
        if (rateFrom != null) {
            dataPredicates.add(dataCriteriaBuilder.greaterThanOrEqualTo(beers.get("rate"), rateFrom));
            countPredicates.add(countCriteriaBuilder.greaterThanOrEqualTo(root.get("rate"), rateFrom));
        }
        if (rateTo != null) {
            dataPredicates.add(dataCriteriaBuilder.lessThanOrEqualTo(beers.get("rate"), rateTo));
            countPredicates.add(countCriteriaBuilder.lessThanOrEqualTo(root.get("rate"), rateTo));
        }
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        dataQuery.where(dataPredicates.toArray(new Predicate[0]));

        Long totalResults = entityManager.createQuery(countQuery).getSingleResult();

        List<Beer> beerList = entityManager.createQuery(dataQuery)
                .setMaxResults(paging.getPageSize())
                .setFirstResult(paging.getPageNumber())
                .getResultList();
        List<BeerDTO> beerDTOList = beerList.stream().map(this::convertToBeerDto)
                .collect(Collectors.toList());

        wrapper.setBeers(beerDTOList);
        wrapper.setTotalItems(totalResults);
        wrapper.setTotalPages((int) (totalResults/paging.getPageSize()));
        wrapper.setCurrentPage(paging.getPageNumber());
        return wrapper;
    }

    private BeerDTO convertToBeerDto(Beer beer) {
        BeerDTO dto = modelMapper.map(beer, BeerDTO.class);
        return dto;
    }
}
