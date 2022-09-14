package com.pvdong.lesson3.service;

import com.pvdong.lesson3.dto.HorseDto;
import com.pvdong.lesson3.entity.Horse;
import com.pvdong.lesson3.exception.HorseAlreadyExistsException;
import com.pvdong.lesson3.repository.HorseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    @Autowired
    private HorseRepository horseRepository;

    @Override
    public List<Horse> findByTrainerIdAndYear(Integer trainerId, Integer year) {
        return horseRepository.customFilterMethod(trainerId, year);
    }

    @Override
    public List<Horse> findByTrainerId(Integer trainerId) {
        return horseRepository.getHorseListByTrainerId(trainerId);
    }

    @Override
    public List<Horse> findAllHorses() {
        return horseRepository.findAll();
    }

    @Override
    public Horse saveHorse(Horse horse) {
        if (horseRepository.existsByName(horse.getName())) {
            throw new HorseAlreadyExistsException();
        }
        horseRepository.save(horse);
        return horse;
    }

    @Override
    public Horse findHorseById(Integer id) {
        return horseRepository.findById(id).get();
    }

    @Override
    public Horse findByName(String name) {
        return horseRepository.getByName(name);
    }

    @Override
    public Horse update(Integer id, HorseDto horseDto) {
        Horse foundHorse = findHorseById(id);
        foundHorse.setName(horseDto.getName());
        foundHorse.setFoaled(horseDto.getFoaled());
        horseRepository.save(foundHorse);
        return foundHorse;
    }

    @Override
    public void updateHorseFoaled(Integer id, Date foaled) {
        Horse foundHorse = findHorseById(id);
        foundHorse.setFoaled(foaled);
        horseRepository.save(foundHorse);
        Horse foundHorse2 = findHorseById(id);
        System.out.println(foundHorse2);
        System.out.println("ok");
    }

    @Override
    public void deleteHorseById(Integer id) {
        horseRepository.deleteById(id);
    }
}
