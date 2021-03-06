package pl.adiks.msscbeerservice.controller;
;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adiks.msscbeerservice.model.BeerDTO;
import pl.adiks.msscbeerservice.model.BeerPagedList;
import pl.adiks.msscbeerservice.model.BeerStyleEnum;
import pl.adiks.msscbeerservice.services.BeerService;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = { "application/json" }, path = "beer")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){

        if (showInventoryOnHand == null)
            showInventoryOnHand = false;

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/beer/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID beerId, @RequestParam(required = false) Boolean showInventoryOnHand) throws NotFoundException {

        if (showInventoryOnHand == null)
            showInventoryOnHand = false;

        return new ResponseEntity<>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @PostMapping("/beer")
    public ResponseEntity<BeerDTO> saveNewBeer(@Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerService.saveNewBeer(beerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeerById(@PathVariable UUID beerId, @Valid @RequestBody BeerDTO beerDTO) throws NotFoundException {
            return new ResponseEntity<>(beerService.updateBeerById(beerId, beerDTO), HttpStatus.OK);
    }

    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDTO> getBeerByUpc(@PathVariable String upc,
                                                @RequestParam(required = false) Boolean showInventoryOnHand)
            throws NotFoundException {

        if (showInventoryOnHand == null)
            showInventoryOnHand = false;

        return new ResponseEntity<>(beerService.getByUpc(upc, showInventoryOnHand), HttpStatus.OK);
    }
}
