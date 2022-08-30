package com.demo.sdguiwbe22.dto;

import com.demo.sdguiwbe22.entities.BeerType;
import com.demo.sdguiwbe22.wrapper.BeerTypeSubset;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BeerDTO {
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    @BeerTypeSubset(anyOf = {"lager", "pilsner"}, message = "Only Lager or Pilsner")
    private String type;

    @NotNull
    @Min(value = 1 , message = "Value should be greater then equal to 1")
    @Max(value = 5 , message = "Value should be less then equal to 5")
    private Integer rate;
}
