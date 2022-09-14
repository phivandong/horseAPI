package com.pvdong.lesson3;

import com.pvdong.lesson3.controller.HorseController;
import com.pvdong.lesson3.entity.Horse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class IntegrationTests {
    @Autowired
    private HorseController horseController;

    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Horse horse = new Horse();
        horse.setName("Snips");
        horse.setFoaled(sdf.parse("2012-06-10 04:14:17.391"));

        ResponseEntity<Horse> horseResult = horseController.createHorse(horse);
        Integer id = horseResult.getBody().getId();

        ResponseEntity<List<Horse>> horses = horseController.getAllHorses();
        assertThat(horses.getBody()).element(14).hasFieldOrPropertyWithValue("name", "Snips");
        horseController.deleteHorse(id);
        assertThat(horseController.getAllHorses().getBody()).extracting(Horse::getId).doesNotContain(id);
    }
}
