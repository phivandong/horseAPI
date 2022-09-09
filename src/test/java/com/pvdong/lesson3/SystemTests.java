package com.pvdong.lesson3;

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
    public void testCreateReadDelete() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = new URL("http://localhost:" + port + "/api/horse").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Horse horse1 = new Horse();
        horse1.setName("Snips");
        horse1.setFoaled(sdf.parse("2012-06-10 04:14:17.391"));

        // System throw exception
        Horse horse2 = new Horse();
        horse2.setName("Pony");
        horse2.setFoaled(sdf.parse("2002-06-10 04:14:17.391"));
        assertThatThrownBy(() -> restTemplate.postForEntity(url, horse2, Horse.class)).hasMessageContaining("\"status\":409");

        ResponseEntity<Horse> entity = restTemplate.postForEntity(url, horse1, Horse.class);
        Integer horseId = entity.getBody().getId();
        Horse[] horses = restTemplate.getForObject(url + "/all", Horse[].class);
        assertThat(horses).extracting(Horse::getName).contains("Snips");

        restTemplate.delete(url + "/" + horseId);
        Horse[] horsesAfterDelete = restTemplate.getForObject(url + "/all", Horse[].class);
        assertThat(horsesAfterDelete).extracting(Horse::getName).doesNotContain("Snips");
        assertThat(restTemplate.getForObject(url + "/all", Horse[].class)).isNotEmpty();
    }
}
