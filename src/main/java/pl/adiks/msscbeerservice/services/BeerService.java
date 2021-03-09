package pl.adiks.msscbeerservice.services;

import javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;
import pl.adiks.msscbeerservice.model.BeerDTO;
import pl.adiks.msscbeerservice.model.BeerPagedList;
import pl.adiks.msscbeerservice.model.BeerStyleEnum;

import java.util.UUID;

public interface BeerService {

    BeerDTO getById(UUID beerId, boolean showInventoryOnHand) throws NotFoundException;

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO) throws NotFoundException;

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand);
}
