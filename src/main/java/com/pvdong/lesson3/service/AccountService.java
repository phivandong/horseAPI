package com.pvdong.lesson3.service;

import com.pvdong.lesson3.dto.AccountDto;
import com.pvdong.lesson3.entity.Account;
import com.pvdong.lesson3.entity.JwtRequest;
import com.pvdong.lesson3.entity.JwtResponse;

public interface AccountService {
    Account findAccountById(Integer id);
    Account registration(AccountDto accountDto);
    Account changePassword(Integer id, String password);
    JwtResponse login(JwtRequest jwtRequest);
}
