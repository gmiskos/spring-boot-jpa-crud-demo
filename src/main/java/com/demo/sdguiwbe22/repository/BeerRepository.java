package com.demo.sdguiwbe22.repository;

import com.demo.sdguiwbe22.entities.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {

    Page<Beer> findAll(Pageable pageable);
}
