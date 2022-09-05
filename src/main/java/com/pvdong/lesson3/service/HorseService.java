package com.pvdong.lesson3.service;

import com.pvdong.lesson3.dto.HorseDto;
import com.pvdong.lesson3.entity.Horse;

import java.util.Date;
import java.util.List;

public interface HorseService {
    List<Horse> findByTrainerIdAndYear(Integer trainerId, Integer year);
    List<Horse> findByTrainerId(Integer trainerId);
    List<Horse> findAllHorses();
    Horse saveHorse(Horse horse);
    Horse findHorseById(Integer id);
    Horse findByName(String name);
    Horse update(Integer id, HorseDto horseDto);
    void updateHorseFoaled(Integer id, Date foaled);
    void deleteHorseById(Integer id);
}
