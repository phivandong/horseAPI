package com.pvdong.lesson3;

import com.pvdong.lesson3.entity.Horse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.text.SimpleDateFormat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SystemTests {
    @LocalServerPort
    private int port;

    @Test
    public void testCreateReadDelete() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = new URL("http://localhost:" + port + "/api/horse").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Horse horse = new Horse();
        horse.setName("Snips");
        horse.setFoaled(sdf.parse("2012-06-10 04:14:17.391"));
        ResponseEntity<Horse> entity = restTemplate.postForEntity(url, horse, Horse.class);
        Integer horseId = entity.getBody().getId();
        Horse[] horses = restTemplate.getForObject(url + "/all", Horse[].class);
        Assertions.assertThat(horses).extracting(Horse::getName).contains("Snips");

//        restTemplate.delete(url + "/" + entity.getBody().getId());
        restTemplate.delete(url + "/" + horseId);
        Assertions.assertThat(restTemplate.getForObject(url + "/all", Horse[].class)).isNotEmpty();
    }
}
