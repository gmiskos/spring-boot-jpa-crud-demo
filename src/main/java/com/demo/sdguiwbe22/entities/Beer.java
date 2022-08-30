package com.demo.sdguiwbe22.entities;

import com.demo.sdguiwbe22.wrapper.BeerTypeSubset;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Table(name = "beers")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Beer{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BeerType type;

    private Integer rate;
}
