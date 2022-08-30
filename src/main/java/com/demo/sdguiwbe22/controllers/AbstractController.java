package com.demo.sdguiwbe22.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AbstractController.BASE_URI)
public class AbstractController {
    public final static String BASE_URI = "/api";
}