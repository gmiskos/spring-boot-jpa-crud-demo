package com.demo.sdguiwbe22.wrapper;

import com.demo.sdguiwbe22.entities.BeerType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeerTypeSubSetValidator implements ConstraintValidator<BeerTypeSubset, String> {
    private List<String> subset;

    @Override
    public void initialize(BeerTypeSubset constraint) {
        this.subset = List.of(constraint.anyOf());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return subset.stream().map(s->s.toUpperCase()).anyMatch(n->
                n.equals(value.toUpperCase()));
        //List<String> uSubset = subset.stream().map(s -> s.toUpperCase()).collect(Collectors.toList());
        //String type = value.toUpperCase();
        //return uSubset.contains(type);
    }
}
