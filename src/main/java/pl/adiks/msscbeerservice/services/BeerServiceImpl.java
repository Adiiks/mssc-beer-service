package pl.adiks.msscbeerservice.services;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adiks.msscbeerservice.entity.Beer;
import pl.adiks.msscbeerservice.mappers.BeerMapper;
import pl.adiks.msscbeerservice.model.BeerDTO;
import pl.adiks.msscbeerservice.repository.BeerRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDTO getById(UUID beerId) throws NotFoundException {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException("Beer not found. Wrong id")));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO) throws NotFoundException {

        Beer beerToUpdate = beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException("Beer not found. Wrong id"));

        beerToUpdate.setBeerName(beerDTO.getBeerName());
        beerToUpdate.setBeerStyle(beerDTO.getBeerStyle().name());
        beerToUpdate.setPrice(beerDTO.getPrice());
        beerToUpdate.setUpc(beerDTO.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beerToUpdate));
    }
}
