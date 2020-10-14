package pl.adiks.msscbeerservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adiks.msscbeerservice.model.BeerDTO;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID beerId) {
        return new ResponseEntity<>(BeerDTO.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewBeer(@RequestBody BeerDTO beerDTO) {
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@PathVariable UUID beerId, @RequestBody BeerDTO beerDTO) {
    }
}
