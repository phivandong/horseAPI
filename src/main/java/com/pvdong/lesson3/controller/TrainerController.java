package com.pvdong.lesson3.controller;

import com.pvdong.lesson3.dto.TrainerDto;
import com.pvdong.lesson3.dto.TrainerDtoNoAccount;
import com.pvdong.lesson3.entity.Trainer;
import com.pvdong.lesson3.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping(path = "/trainer-no-account")
    public ResponseEntity<Trainer> createTrainerNoAccount(@RequestBody TrainerDtoNoAccount trainerDto) {
        return new ResponseEntity<>(trainerService.createTrainerNoAccount(trainerDto), HttpStatus.OK);
    }

    @PostMapping(path = "/trainer")
    public ResponseEntity<Trainer> createTrainer(@RequestBody TrainerDto trainerDto) {
        return new ResponseEntity<>(trainerService.createTrainer(trainerDto), HttpStatus.OK);
    }

    @GetMapping(path = "/trainer/{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable Integer id) {
        return new ResponseEntity<>(trainerService.findTrainerById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/trainer/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Integer id, @RequestBody TrainerDto trainerDto) {
        return new ResponseEntity<>(trainerService.updateTrainerById(id, trainerDto), HttpStatus.OK);
    }

    @PutMapping(path = "/trainer/add-horse/{trainerId}")
    public ResponseEntity<Trainer> addNewHorse(@PathVariable Integer trainerId, @RequestParam("horseId") Integer horseId) {
        trainerService.addNewHorse(trainerId, horseId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/trainer/{id}")
    public ResponseEntity<HttpStatus> deleteTrainer(@PathVariable Integer id) {
        trainerService.deleteTrainerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}