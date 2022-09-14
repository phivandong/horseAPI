package com.pvdong.lesson3;

import com.pvdong.lesson3.dto.HorseDto;
import com.pvdong.lesson3.entity.Horse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SystemTests {
    @LocalServerPort
    private int port;

    @Test
    public void testCreateReadPutDelete() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = new URL("http://localhost:" + port + "/api/horse").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Horse horse1 = new Horse();
        horse1.setName("Snips");
        horse1.setFoaled(sdf.parse("2012-06-10 04:14:17.391"));

        // system throw exception
        Horse horse2 = new Horse();
        horse2.setName("Pony");
        horse2.setFoaled(sdf.parse("2002-06-10 04:14:17.391"));
        assertThatThrownBy(() -> restTemplate.postForEntity(url, horse2, Horse.class)).hasMessageContaining("\"status\":409");

        // create new horse
        ResponseEntity<Horse> entity = restTemplate.postForEntity(url, horse1, Horse.class);
        Integer horseId = entity.getBody().getId();
        Horse[] horses = restTemplate.getForObject(url + "/all", Horse[].class);
        assertThat(horses).extracting(Horse::getName).contains("Snips");

        // get horse by id
        Horse foundHorseById = restTemplate.getForObject(url + "/" + horseId, Horse.class);
        assertThat(foundHorseById).extracting(Horse::getName).isEqualTo("Snips");

        // update horse
        HorseDto horseDto = new HorseDto("New Snips", sdf.parse("2012-06-10 04:14:17.391"));
        restTemplate.put(url + "/" + horseId, horseDto);
        Horse foundHorseByIdAfterUpdate = restTemplate.getForObject(url + "/" + horseId, Horse.class);
        assertThat(foundHorseByIdAfterUpdate).extracting(Horse::getName).isEqualTo("New Snips");

        // delete horse
        restTemplate.delete(url + "/" + horseId);
        Horse[] horsesAfterDelete = restTemplate.getForObject(url + "/all", Horse[].class);
        assertThat(horsesAfterDelete).extracting(Horse::getName).doesNotContain("Snips");
        assertThat(restTemplate.getForObject(url + "/all", Horse[].class)).isNotEmpty();
    }
}
