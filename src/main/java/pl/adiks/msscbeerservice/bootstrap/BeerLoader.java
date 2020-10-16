package pl.adiks.msscbeerservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.adiks.msscbeerservice.entity.Beer;
import pl.adiks.msscbeerservice.repository.BeerRepository;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {

        if (beerRepository.count() == 0) {

            beerRepository.save(Beer.builder()
                .beerName("Tyskie")
                .beerStyle("Pszenne")
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(33783219L)
                .build());

            beerRepository.save(Beer.builder()
                    .beerName("Lech")
                    .beerStyle("Gorzkie")
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .price(new BigDecimal("11.95"))
                    .upc(33743219L)
                    .build());
        }

        System.out.println("Loaded Beers: " + beerRepository.count());
    }
}
