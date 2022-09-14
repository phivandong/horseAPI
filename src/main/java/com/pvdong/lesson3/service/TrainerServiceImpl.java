package com.pvdong.lesson3.service;

import com.pvdong.lesson3.dto.TrainerDto;
import com.pvdong.lesson3.dto.TrainerDtoNoAccount;
import com.pvdong.lesson3.entity.Account;
import com.pvdong.lesson3.entity.HorseAccount;
import com.pvdong.lesson3.entity.Trainer;
import com.pvdong.lesson3.repository.AccountRepository;
import com.pvdong.lesson3.repository.HorseAccountRepository;
import com.pvdong.lesson3.repository.HorseRepository;
import com.pvdong.lesson3.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private HorseAccountRepository horseAccountRepository;

    @Override
    public Trainer createTrainerNoAccount(TrainerDtoNoAccount trainerDto) {
        Trainer trainer = new Trainer(trainerDto.getName());
        trainerRepository.save(trainer);
        return trainer;
    }

    @Override
    public Trainer createTrainer(TrainerDto trainerDto) {
        Account foundAccount = accountRepository.getById(trainerDto.getAccount_id());
        Trainer trainer = new Trainer(trainerDto.getName(), foundAccount);
        trainerRepository.save(trainer);
        return trainer;
    }

    @Override
    public Trainer findTrainerById(Integer id) {
        return trainerRepository.findById(id).get();
    }

    @Override
    public void deleteTrainerById(Integer id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public Trainer updateTrainerById(Integer id, TrainerDto trainerDto) {
        Trainer foundTrainer = findTrainerById(id);
        foundTrainer.setName(trainerDto.getName());
        return trainerRepository.save(foundTrainer);
    }

    @Override
    public void addNewHorse(Integer trainerId, Integer horseId) {
        Trainer foundTrainer = trainerRepository.getById(trainerId);
        Account alreadyAccount = foundTrainer.getAccount();
        Account foundAccount = accountRepository.getAccountByHorseId(horseId);
        if (foundAccount != null && !alreadyAccount.getId().equals(foundAccount.getId())) {
            foundTrainer.setAccount(foundAccount);
            trainerRepository.save(foundTrainer);
            System.out.println("Added new horse to new account!");
        } else {
//            Horse horse = horseRepository.getById(horseId);
//            List<Horse> horseList = alreadyAccount.getHorses();
//            horseList.add(horse);
//            alreadyAccount.setHorses(horseList);
            HorseAccount horseAccount = new HorseAccount(14, alreadyAccount.getId(), horseId, true);
            horseAccountRepository.save(horseAccount);
            System.out.println("Added new horse to exist account!");
        }
    }
}
