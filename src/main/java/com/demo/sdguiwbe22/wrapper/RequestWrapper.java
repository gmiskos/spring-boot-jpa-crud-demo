package com.demo.sdguiwbe22.wrapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestWrapper {
    private String name;
    private Integer rateFrom;
    private Integer rateTo;
    private String type;
    private int page = 0;
    private int size = 10;
}
