package com.pvdong.lesson3.controller;

import com.pvdong.lesson3.dto.HorseDto;
import com.pvdong.lesson3.entity.Horse;
import com.pvdong.lesson3.service.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class HorseController {

    @Autowired
    private HorseService horseService;

    @GetMapping(path = "/horse/all")
    public ResponseEntity<List<Horse>> getAllHorses() {
        return new ResponseEntity<>(horseService.findAllHorses(), HttpStatus.OK);
    }

    @GetMapping(path = "/horse/{id}")
    public ResponseEntity<Horse> getHorseById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(horseService.findHorseById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/horse")
    public ResponseEntity<List<Horse>> getHorsesByIdAndYear(@RequestParam("trainer_id") Integer id, @RequestParam("year") Integer year) {
        return new ResponseEntity<>(horseService.findByTrainerIdAndYear(id, year), HttpStatus.OK);
    }

    @GetMapping(path = "/horse-trainer")
    public ResponseEntity<List<Horse>> getHorsesByIdAndYear(@RequestParam("trainer_id") Integer id) {
        return new ResponseEntity<>(horseService.findByTrainerId(id), HttpStatus.OK);
    }

    @PostMapping(path = "/horse")
    public ResponseEntity<Horse> createHorse(@RequestBody Horse horse) {
        return new ResponseEntity<>(horseService.saveHorse(horse), HttpStatus.CREATED);
    }

    @PutMapping(path = "/horse/{id}")
    public ResponseEntity<Horse> updateHorse(@PathVariable Integer id, @RequestBody HorseDto horseDto) {
        return new ResponseEntity<>(horseService.update(id, horseDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/horse/{id}")
    public ResponseEntity<HttpStatus> deleteHorse(@PathVariable Integer id) {
        horseService.deleteHorseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
