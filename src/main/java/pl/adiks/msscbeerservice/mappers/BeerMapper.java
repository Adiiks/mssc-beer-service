package pl.adiks.msscbeerservice.mappers;

import org.mapstruct.Mapper;
import pl.adiks.msscbeerservice.entity.Beer;
import pl.adiks.msscbeerservice.model.BeerDTO;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDTO beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDtoWithInventory(Beer beer);
}
