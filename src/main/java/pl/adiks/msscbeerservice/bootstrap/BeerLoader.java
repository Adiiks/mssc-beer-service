package pl.adiks.msscbeerservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.adiks.msscbeerservice.entity.Beer;
import pl.adiks.msscbeerservice.repository.BeerRepository;

import java.math.BigDecimal;

//@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

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
                .upc(BEER_1_UPC)
                .build());

            beerRepository.save(Beer.builder()
                    .beerName("Lech")
                    .beerStyle("Gorzkie")
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .price(new BigDecimal("11.95"))
                    .upc(BEER_2_UPC)
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Haineken")
                    .beerStyle("Słodko-Gorzkie")
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .price(new BigDecimal("10.95"))
                    .upc(BEER_3_UPC)
                    .build());
        }

        System.out.println("Loaded Beers: " + beerRepository.count());
    }
}
