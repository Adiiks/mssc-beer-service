package pl.adiks.msscbeerservice.services;

import javassist.NotFoundException;
import pl.adiks.msscbeerservice.model.BeerDTO;

import java.util.UUID;

public interface BeerService {

    BeerDTO getById(UUID beerId) throws NotFoundException;

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO) throws NotFoundException;
}
