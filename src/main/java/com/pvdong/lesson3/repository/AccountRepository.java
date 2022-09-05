package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, AccountRepositoryBase {
    Account findByUsername(String username);
}
