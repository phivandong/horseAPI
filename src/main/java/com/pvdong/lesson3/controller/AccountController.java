package com.pvdong.lesson3.controller;

import com.pvdong.lesson3.dto.AccountDto;
import com.pvdong.lesson3.entity.Account;
import com.pvdong.lesson3.entity.JwtRequest;
import com.pvdong.lesson3.entity.JwtResponse;
import com.pvdong.lesson3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        return new ResponseEntity<>(accountService.findAccountById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/account")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.registration(accountDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/account/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest jwtRequest) {
        return new ResponseEntity<>(accountService.login(jwtRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/account/change_password/{id}")
    public ResponseEntity<Account> changePassword(@PathVariable Integer id, @RequestBody String password) {
        return new ResponseEntity<>(accountService.changePassword(id, password), HttpStatus.OK);
    }

}
