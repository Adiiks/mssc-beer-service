package pl.adiks.msscbeerservice.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import pl.adiks.msscbeerservice.entity.Beer;
import pl.adiks.msscbeerservice.model.BeerDTO;
import pl.adiks.msscbeerservice.services.inventory.BeerInventoryService;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO beerToBeerDto(Beer beer) {
        return mapper.beerToBeerDto(beer);
    }

    public BeerDTO beerToBeerDtoWithInventory(Beer beer) {
        BeerDTO dto = mapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDTO beerDto) {
        return mapper.beerDtoToBeer(beerDto);
    }
}
