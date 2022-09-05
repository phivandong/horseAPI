package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.Account;

public interface AccountRepositoryBase {
    Account getAccountByHorseId(Integer horseId);
}
