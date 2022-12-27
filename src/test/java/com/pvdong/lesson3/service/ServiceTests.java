package com.pvdong.lesson3.service;

import com.pvdong.lesson3.entity.Account;
import com.pvdong.lesson3.entity.Horse;
import com.pvdong.lesson3.entity.HorseAccount;
import com.pvdong.lesson3.entity.Trainer;
import com.pvdong.lesson3.exception.HorseAlreadyExistsException;
import com.pvdong.lesson3.repository.AccountRepository;
import com.pvdong.lesson3.repository.HorseAccountRepository;
import com.pvdong.lesson3.repository.HorseRepository;
import com.pvdong.lesson3.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    @InjectMocks
    private HorseServiceImpl horseService;

    @Mock
    private HorseRepository horseRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private HorseAccountRepository horseAccountRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void testSaveHorse() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Horse horse1 = new Horse(9, "Snips", sdf.parse("2010-07-01 01:14:17.391"));
        Horse horse2 = new Horse(10, "Cattail", sdf.parse("2010-07-01 04:14:17.391"));
        List<Horse> horses = Arrays.asList(horse1, horse2);
        lenient().when(horseRepository.findAll()).thenReturn(horses);
        lenient().when(horseRepository.getById(9)).thenReturn(horse1);
        lenient().when(horseRepository.getById(10)).thenReturn(horse2);

        lenient().when(horseService.saveHorse(horse1)).thenReturn(horse1);
        lenient().when(horseService.saveHorse(horse2)).thenReturn(horse2);

        Horse horse3 = new Horse(11, "Snips", sdf.parse("2020-07-01 01:14:17.391"));

        lenient().when(horseRepository.findAll()).thenReturn(horses);

        List<Horse> horseList = horseService.findAllHorses();
        System.out.println(horseList);

        lenient().when(horseRepository.existsByName(horse3.getName())).thenReturn(true);
        assertThatThrownBy(() -> horseService.saveHorse(horse3)).isInstanceOf(HorseAlreadyExistsException.class);

        System.out.println(horseList);
        assertThat(horseList.size()).isEqualTo(2);
        verify(horseRepository, times(1)).findAll();
    }

    @Test
    void testFindHorse() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Horse expectedHorse = new Horse(13, "Spitfire", sdf.parse("2020-07-01 01:14:17.391"));
        when(horseRepository.findById(anyInt())).thenReturn(Optional.of(expectedHorse));

        final Horse actual = horseService.findHorseById(getRandomInt());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedHorse);
        verify(horseRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(horseRepository);
    }

    @Test
    void testAddHorses() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Horse horse1 = new Horse(13, "Spitfire", sdf.parse("2020-07-01 01:14:17.391"));
        Horse horse2 = new Horse(14, "Spike", sdf.parse("2010-07-01 04:14:17.391"));
        Horse horse3 = new Horse(15, "Queen", sdf.parse("2011-07-01 04:14:17.391"));
        List<Horse> horses = Arrays.asList(horse1, horse2, horse3);
        List<Horse> requiredQuery = Arrays.asList(horse1, horse3);
        lenient().when(horseRepository.findAll()).thenReturn(horses);
        lenient().when(horseRepository.getById(13)).thenReturn(horse1);
        lenient().when(horseRepository.getById(14)).thenReturn(horse2);
        lenient().when(horseRepository.getById(15)).thenReturn(horse3);

        Account account1 = new Account(10, "ConGido", "ConGido123", true);
        Account account2 = new Account(11, "ConQuy", "ConQuy123", true);
        List<Account> accounts = Arrays.asList(account1, account2);
        lenient().when(accountRepository.findAll()).thenReturn(accounts);
        lenient().when(accountRepository.getById(10)).thenReturn(account1);
        lenient().when(accountRepository.getById(11)).thenReturn(account2);

        HorseAccount horseAccount1 = new HorseAccount(12, 10, 13, true);
        HorseAccount horseAccount2 = new HorseAccount(13, 11, 14, true);
        List<HorseAccount> horseAccounts = Arrays.asList(horseAccount1, horseAccount2);
        lenient().when(horseAccountRepository.findAll()).thenReturn(horseAccounts);
        lenient().when(horseAccountRepository.getById(12)).thenReturn(horseAccount1);
        lenient().when(horseAccountRepository.getById(13)).thenReturn(horseAccount2);

        Trainer trainer1 = new Trainer(10, "Naruto", account2);
        Trainer trainer2 = new Trainer(11, "Sakura", account2);
        List<Trainer> trainers = Arrays.asList(trainer1, trainer2);
        lenient().when(trainerRepository.findAll()).thenReturn(trainers);
        lenient().when(trainerRepository.getById(10)).thenReturn(trainer1);
        lenient().when(trainerRepository.getById(11)).thenReturn(trainer2);

        lenient().when(accountRepository.getAccountByHorseId(13)).thenReturn(account1);
        lenient().when(accountRepository.getAccountByHorseId(14)).thenReturn(account2);

        lenient().when(horseRepository.getHorseListByTrainerId(10)).thenReturn(requiredQuery);

        trainerService.addNewHorse(10, 13);
        trainerService.addNewHorse(10, 15);

        List<Horse> foundHorseList = horseService.findByTrainerId(10);
        System.out.println(foundHorseList);
        assertThat(foundHorseList).isEqualTo(requiredQuery);
    }

    private int getRandomInt() {
        return new Random().ints(1, 10).findFirst().getAsInt();
    }
}