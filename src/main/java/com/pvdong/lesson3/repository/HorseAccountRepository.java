package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.HorseAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseAccountRepository extends JpaRepository<HorseAccount, Integer> {
}
