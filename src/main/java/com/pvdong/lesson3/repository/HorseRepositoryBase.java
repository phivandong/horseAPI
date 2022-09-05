package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.Horse;

import java.util.List;

public interface HorseRepositoryBase {
    List<Horse> customFilterMethod(Integer trainerId, Integer year);
    List<Horse> getHorseListByTrainerId(Integer trainerId);
}
