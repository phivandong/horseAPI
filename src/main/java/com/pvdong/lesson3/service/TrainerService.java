package com.pvdong.lesson3.service;

import com.pvdong.lesson3.dto.TrainerDto;
import com.pvdong.lesson3.dto.TrainerDtoNoAccount;
import com.pvdong.lesson3.entity.Trainer;

public interface TrainerService {
    Trainer createTrainerNoAccount(TrainerDtoNoAccount trainerDto);
    Trainer createTrainer(TrainerDto trainerDto);
    Trainer findTrainerById(Integer id);
    void deleteTrainerById(Integer id);
    Trainer updateTrainerById(Integer id, TrainerDto trainerDto);
    void addNewHorse(Integer trainerId, Integer horseId);
}
