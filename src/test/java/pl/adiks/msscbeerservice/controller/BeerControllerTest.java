package pl.adiks.msscbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.adiks.msscbeerservice.bootstrap.BeerLoader;
import pl.adiks.msscbeerservice.model.BeerDTO;
import pl.adiks.msscbeerservice.model.BeerStyleEnum;
import pl.adiks.msscbeerservice.services.BeerService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {

        when(beerService.getById(any(UUID.class), anyBoolean())).thenReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {

        BeerDTO beerDTO = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        when(beerService.saveNewBeer(any(BeerDTO.class))).thenReturn(beerDTO);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {

        BeerDTO beerDTO = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        when(beerService.updateBeerById(any(UUID.class), any(BeerDTO.class))).thenReturn(beerDTO);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void getBeerByUpc() throws Exception {

        when(beerService.getByUpc(anyString(), anyBoolean())).thenReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beerUpc/" + getValidBeerDto().getUpc()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    BeerDTO getValidBeerDto(){
        return BeerDTO
                .builder()
                .beerName("My beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal(2.65))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }
}