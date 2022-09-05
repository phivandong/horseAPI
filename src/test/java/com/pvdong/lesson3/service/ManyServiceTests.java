package com.pvdong.lesson3.service;

import com.pvdong.lesson3.entity.Horse;
import com.pvdong.lesson3.exception.HorseAlreadyExistsException;
import com.pvdong.lesson3.repository.HorseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Spring Boot integration test service layer
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class ManyServiceTests {
    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private HorseService horseService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void newHorse() throws ParseException {
        /* Test save Horse */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Horse horse1 = new Horse();
        horse1.setName("Fluttershy");
        horse1.setFoaled(sdf.parse("2015-07-01 04:14:17.391"));

        Horse persistedHorse = horseService.saveHorse(horse1);

        assertThat(persistedHorse).isNotNull();
        assertThat(persistedHorse.getId()).isNotNull();
        assertThat(horseService.findByName("Fluttershy")).isEqualTo(persistedHorse);
        assertThat(persistedHorse.getName()).isEqualTo(horse1.getName());
        assertThat(persistedHorse.getFoaled()).isEqualTo(horse1.getFoaled());

        /* Test save Horse be thrown because name duplicate */
        Horse horse2 = new Horse();
        horse2.setName("Pony");
        horse2.setFoaled(sdf.parse("2017-07-01 04:14:17.391"));
        assertThatThrownBy(() -> horseService.saveHorse(horse2)).isInstanceOf(HorseAlreadyExistsException.class);

        /* Test modify Horse to increase 1 year */
        Horse horse3 = new Horse(3, "Rarity", sdf.parse("2018-07-01 04:14:17.391"));
        Horse horse4 = new Horse(12, "Fleetfoot", sdf.parse("2011-06-10 04:14:17.391"));

        List<Horse> expectedList = Arrays.asList(horse3, horse4);

        List<Horse> queryList = jdbcTemplate.query("select h.id, h.name, date_sub(h.foaled, interval 1 year) as foaled " +
                "from horse h " +
                "join horse_account hc on h.id = hc.horse_id " +
                "join account a on hc.account_id = a.id " +
                "join trainer t on a.id = t.account_id " +
                "where t.id = 9", new BeanPropertyRowMapper<>(Horse.class));

        System.out.println("Query List: " + queryList);

        SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd HH:mm:ss.SSS z yyyy");

//        queryList.forEach(horse -> {
//            try {
//                horse.setFoaled(sdf2.parse(sdf2.format(horse.getFoaled())));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });

        List<Horse> horseList = horseService.findByTrainerId(9);
        System.out.println("Before: " + horseList);
//        horseList.forEach(horse -> {
//            System.out.println("Age of " + horse.getName() + " is: " + getAge(horse.getFoaled()));
//            horse.setFoaled(addOntYearToAge(horse.getFoaled()));
//            System.out.println("Age of " + horse.getName() + " is: " + getAge(horse.getFoaled()));
//        });
        horseList.forEach(horse -> {
            System.out.println("Age of " + horse.getName() + " is: " + getAge(horse.getFoaled()));
            horseService.updateHorseFoaled(horse.getId(), addOntYearToAge(horse.getFoaled()));
            System.out.println("Age of " + horse.getName() + " is: " + getAge(horse.getFoaled()));
        });
        System.out.println("After: " + horseList);
//        horseRepository.saveAll(horseList);
//        horseList.forEach(horse -> {
//            horseRepository.save(horse);
//        });
        List<Horse> listAfterModify = horseService.findByTrainerId(9);
        System.out.println(listAfterModify);

//        listAfterModify.forEach(horse -> {
//            try {
//                horse.setFoaled(sdf2.parse(sdf2.format(horse.getFoaled())));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });

        List<Horse> requiredList = jdbcTemplate.query("select h.* " +
                "from horse h " +
                "join horse_account hc on h.id = hc.horse_id " +
                "join account a on hc.account_id = a.id " +
                "join trainer t on a.id = t.account_id " +
                "where t.id = 9", new BeanPropertyRowMapper<>(Horse.class));

        System.out.println(jdbcTemplate.query("select * from horse", new BeanPropertyRowMapper<>(Horse.class)));
//        requiredList.forEach(horse -> {
//            try {
//                horse.setFoaled(sdf2.parse(sdf2.format(horse.getFoaled())));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });

        assertThat(requiredList).isEqualTo(queryList);
//        assertThat(requiredList).isEqualTo(new ArrayList<>(queryList));
//        assertThat(requiredList).isEqualTo(new ArrayList<>(expectedList));
    }

    private Date addOntYearToAge(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    private int getAge(Date birthday) {
        Date currentDate = new Date();
        long timeBetween = currentDate.getTime() - birthday.getTime();
        double yearBetween = timeBetween / 3.15576e+10;
        return (int) Math.floor(yearBetween);
    }
}
