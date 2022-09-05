package com.pvdong.lesson3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pvdong.lesson3.entity.Horse;
import com.pvdong.lesson3.repository.HorseRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(HorseController.class)
@Transactional
public class ControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HorseRepository horseRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public ControllerTests() throws ParseException {
    }

    Horse horse1 = new Horse(1, "Rarity", sdf.parse("2018-07-01 04:14:17.391"));
    Horse horse2 = new Horse(2, "Snails", sdf.parse("2014-06-10 04:14:17.391"));
    Horse horse3 = new Horse(3, "Spike", sdf.parse("2012-06-10 04:14:17.391"));
    Horse horse4 = new Horse(4, "Snips", sdf.parse("2011-06-10 04:14:17.391"));

    @Test
    public void testFindAll() throws Exception {
        List<Horse> horses = Arrays.asList(horse1, horse2, horse3, horse4);
        when(horseRepository.findAll()).thenReturn(horses);
        mockMvc.perform(get("/api/horse/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[1].name", Matchers.is("Snails")));
    }

    @Test
    public void testCreate() throws Exception {
        Horse horse = new Horse(5, "Coco Pommel", sdf.parse("2008-06-15 04:14:17.391"));
        when(horseRepository.save(horse)).thenReturn(horse);

        String record = objectWriter.writeValueAsString(horse);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/horse")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(record);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is("Coco Pommel")));
    }
}
