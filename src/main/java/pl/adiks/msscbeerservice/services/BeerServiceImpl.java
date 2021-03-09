package pl.adiks.msscbeerservice.services;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.adiks.msscbeerservice.entity.Beer;
import pl.adiks.msscbeerservice.mappers.BeerMapper;
import pl.adiks.msscbeerservice.model.BeerDTO;
import pl.adiks.msscbeerservice.model.BeerPagedList;
import pl.adiks.msscbeerservice.model.BeerStyleEnum;
import pl.adiks.msscbeerservice.repository.BeerRepository;

import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(beerPage.getPageable().getPageNumber(),
                                beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());

        return beerPagedList;
    }
}