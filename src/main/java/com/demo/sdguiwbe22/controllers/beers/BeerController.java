package com.demo.sdguiwbe22.controllers.beers;

import com.demo.sdguiwbe22.boundary.BeerBoundary;
import com.demo.sdguiwbe22.controllers.AbstractController;
import com.demo.sdguiwbe22.dto.BeerDTO;
import com.demo.sdguiwbe22.wrapper.BeerWrapper;
import com.demo.sdguiwbe22.wrapper.RequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(AbstractController.BASE_URI  + "/beers")
@CrossOrigin
@Validated
public class BeerController extends AbstractController {
    @Autowired
    BeerBoundary beerBoundary;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<BeerWrapper> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam boolean sorted
    ) {
        return new ResponseEntity<>(beerBoundary.findAllBeers(page, size, sorted), HttpStatus.OK);
    }

    @PostMapping(
            produces= MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BeerDTO>
     createBeer(@Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerBoundary.saveBeer(beerDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Long> deleteBeer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(beerBoundary.deleteBeer(id), HttpStatus.OK);
    }

    @PutMapping(path = "{id}/{rate}")
    public ResponseEntity<BeerDTO> rateBeer(@PathVariable("id") Long id, @PathVariable("rate") @Min(1) @Max(5) Integer rate) throws RestClientException {
        return new ResponseEntity<>(beerBoundary.updateBeer(id, rate), HttpStatus.ACCEPTED);
    }

    @PostMapping(
            path="/search",
            produces= MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    ResponseEntity<BeerWrapper> searchBeers(@RequestBody RequestWrapper requestWrapper) {
        return new ResponseEntity<>(beerBoundary.searchBeers(requestWrapper), HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
