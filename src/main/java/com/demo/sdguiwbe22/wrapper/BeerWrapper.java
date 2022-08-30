package com.demo.sdguiwbe22.wrapper;

import com.demo.sdguiwbe22.dto.BeerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BeerWrapper {
    List<BeerDTO> beers;
    private Integer currentPage;
    private Long totalItems;
    private Integer totalPages;
}
