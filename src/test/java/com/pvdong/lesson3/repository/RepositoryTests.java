package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.Horse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
})
public class RepositoryTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HorseRepositoryBaseImpl horseRepositoryBase;

    @Autowired
    private HorseRepository horseRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(jdbcTemplate).isNotNull();
        assertThat(horseRepositoryBase).isNotNull();
        assertThat(horseRepository).isNotNull();
    }

//    @Test
//    public void cannotSave() throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Horse horse = new Horse(9, "Snips", sdf.parse("2010-07-01 01:14:17.391"));
//
//        Horse horse1 = new Horse(10, "Snips", sdf.parse("2020-07-01 01:14:17.391"));
//        assertThrows(HorseAlreadyExistsException.class, () -> );
//    }

    @Test
    public void testCustomQuery() {
        List<Horse> horses = horseRepositoryBase.customFilterMethod(1, 2010);
        System.out.println(horses);
        assertThat(horses.size()).isEqualTo(2);
        assertThat(horseRepositoryBase.customFilterMethod(1, 2010)).hasSize(2);
    }

    @Test
    public void testCustomQuery2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Horse horse1 = new Horse(9, "Snips", sdf.parse("2020-07-01 01:14:17.391"));
//        Horse horse2 = new Horse(10, "Spike", sdf.parse("2010-07-01 04:14:17.391"));
        Horse horse1 = new Horse(1, "Pony", sdf.parse("2010-06-10 04:14:17.391"));
        Horse horse2 = new Horse(6, "Scootaloo", sdf.parse("2010-11-26 04:14:17.391"));

        List<Horse> horses = Arrays.asList(horse1, horse2);

//        when(jdbcTemplate.query("select h.* from horse h " +
//                "join horse_account hc on h.id = hc.horse_id " +
//                "join account a on hc.account_id = a.id " +
//                "join trainer t on a.id = t.account_id " +
//                "where t.id = 1 and year(h.foaled) = 2010",
//                new BeanPropertyRowMapper<>(Horse.class)))
//                .thenReturn(horses);

        List<Horse> horseList = jdbcTemplate.query("select h.* from horse h " +
                "join horse_account hc on h.id = hc.horse_id " +
                "join account a on hc.account_id = a.id " +
                "join trainer t on a.id = t.account_id " +
                "where t.id = 1 and year(h.foaled) = 2010",
                new BeanPropertyRowMapper<>(Horse.class));
        SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd HH:mm:ss.SSS z yyyy");
        System.out.println(horseList);

        horseList.forEach(horse -> {
            try {
                horse.setFoaled(sdf2.parse(sdf2.format(horse.getFoaled())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
//        List<Horse> horseList = jdbcTemplate.query("select * from horse", new BeanPropertyRowMapper<>(Horse.class));
//        System.out.println(horseList);
        assertThat(horseList.size()).isEqualTo(2);
        assertThat(horseList).isEqualTo(new ArrayList<>(horses));
    }
}
