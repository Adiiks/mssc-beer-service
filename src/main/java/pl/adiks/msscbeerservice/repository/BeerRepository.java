package pl.adiks.msscbeerservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.adiks.msscbeerservice.entity.Beer;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
